

package spring.modern.data.analytics.consumers.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spring.modern.data.analytics.consumers.repository.ProductRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.ProductQuantity;
import spring.modern.data.domains.customer.order.ProductOrder;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static nyla.solutions.core.util.Organizer.toMap;

/**
 * Data access for the retail product data
 * @author gregory green
 */
@Repository
@Slf4j
@ConditionalOnProperty(name = "greenplum",havingValue = "false",matchIfMissing = true)
public class ProductJdbcRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final double confidence;


    private final String findCustomerFavoritesByCustomerIdAndTopCountSql;


    private final String frequentBoughtSql;

    private final String insertSql;

    public ProductJdbcRepository(JdbcTemplate jdbcTemplate,
                                 NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                 @Value("${retail.frequent.bought.confidence:0}")
                                 double confidence,
                                 @Value("${retail.frequent.bought.sql}")
                                 String frequentBoughtSql,
                                 @Value("${retail.favorites.top.sql}")
                                 String findCustomerFavoritesByCustomerIdAndTopCountSql,
                                 @Value("${retail.product.save.sql}")
                                 String insertSql) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.confidence = confidence;
        this.frequentBoughtSql = frequentBoughtSql;
        this.findCustomerFavoritesByCustomerIdAndTopCountSql = findCustomerFavoritesByCustomerIdAndTopCountSql;
        this.insertSql = insertSql;
    }

    @Override
    public CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId, int topCount) {

        final SortedSet<ProductQuantity> productQuantities = new TreeSet<>();


        log.info("customerId: {}, topCount:{}, findCustomerFavoritesByCustomerIdAndTopCountSql: {}",
                customerId,
                topCount,
                findCustomerFavoritesByCustomerIdAndTopCountSql);

        jdbcTemplate.query(findCustomerFavoritesByCustomerIdAndTopCountSql, rs -> {
                    try {
                        var productJson = rs.getString("data");
                        var product = objectMapper.readValue(productJson, Product.class);
                        productQuantities.add(new ProductQuantity(product, rs.getInt("total_quantity")));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                },
                customerId,
                topCount);

        return new CustomerFavorites(customerId, productQuantities);
    }

    @Override
    public List<Product> findFrequentlyBoughtTogether(List<ProductOrder> productOrders) {

        Map<String, ?> map = toMap("productIds", productOrders.stream().map( po -> po.productId()).toList(),
                "confidence",confidence);

        log.info("confidence > {}   productOrders: {} ",confidence, productOrders);
        log.info("Sql: {} ", frequentBoughtSql);


        RowMapper<Product> rowMapper = (rs , rowNum) -> {
            try {
                var boughtCount = rs.getString("times_bought_together");
                var product_cnt = rs.getString("product_cnt");
                var confidence = rs.getString("confidence");

                log.info("boughtCount: {}, product_cnt: {}, confidence: {}",boughtCount,product_cnt,confidence);

                return objectMapper.readValue(rs.getString("data"),Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };

        return this.namedParameterJdbcTemplate.query(frequentBoughtSql,map, rowMapper);
    }

    @SneakyThrows
    @Override
    public void saveProducts(List<Product> products) {

        log.info("sql {} inputs: {}",insertSql,products);

        Map<String, ?>[] maps = new Map[products.size()];

        int i = 0;
        for (Product p: products) {
            maps[i++] = toMap("id",p.id(),"data", objectMapper.writeValueAsString(p));
        }

        namedParameterJdbcTemplate.batchUpdate(insertSql,maps);
    }

}
