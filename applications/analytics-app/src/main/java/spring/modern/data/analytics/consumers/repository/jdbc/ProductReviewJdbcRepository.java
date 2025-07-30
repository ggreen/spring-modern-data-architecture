package spring.modern.data.analytics.consumers.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import spring.modern.data.analytics.consumers.repository.ProductReviewRepository;
import spring.modern.data.domains.customer.reviews.ProductReview;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ProductReviewJdbcRepository repository for the Product review data
 * @author gregory green
 */
@Slf4j
@Repository
public class ProductReviewJdbcRepository implements ProductReviewRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;
    //select DATA from retail.product_reviews where id = ?
    private final String findProductReviewByIdSql;

    /*
     INSERT INTO retail.product_reviews (id, DATA)
            VALUES (?, to_json(?::json))
            ON CONFLICT (id)
            DO UPDATE SET DATA = to_json(?::json);
     */
    private final String updateProductReviewSQL;

    public ProductReviewJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate,
                                       @Value("${retail.product.review.findById.sql}")
                                       String findProductReviewByIdSql,
                                       @Value("${retail.product.review.save.sql}")
                                       String updateProductReviewSQL) {
        this.jdbcTemplate = jdbcTemplate;
        this.findProductReviewByIdSql = findProductReviewByIdSql;
        this.updateProductReviewSQL = updateProductReviewSQL;
    }

    @Override
    public Optional<ProductReview> findById(String productId) {

        log.info("SQL: {} productId:{} ", findProductReviewByIdSql,productId);
        List<ProductReview> productReviews = jdbcTemplate.query(
                findProductReviewByIdSql,
                Map.of("id",productId),
                (rs, i) -> {
            var json = rs.getString(1);
                    try {
                        return objectMapper.readValue(json,ProductReview.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

        log.info("productReview:{} ", productReviews);

        if(productReviews == null || productReviews.isEmpty())
            return Optional.empty();

        return Optional.ofNullable(productReviews.iterator().next());
    }

    @SneakyThrows
    @Override
    public void save(ProductReview productReview) {

        log.info("SQL: {}, productReview: {}",updateProductReviewSQL,productReview);

        var json = objectMapper.writeValueAsString(productReview);
        var cnt = jdbcTemplate.update(updateProductReviewSQL,
                Map.of("id",productReview.id(),
                        "productReviewJson",json)
                );

        log.info("Update count: {} for productReview:{}",cnt,productReview);
    }
}
