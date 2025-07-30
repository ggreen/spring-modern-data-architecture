package spring.modern.data.analytics.consumers.repository.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spring.modern.data.analytics.consumers.repository.ProductReviewRepository;
import spring.modern.data.domains.customer.reviews.ProductReview;

import java.util.List;
import java.util.Optional;

/**
 * ProductReviewJdbcRepository repository for the Product review data
 * @author gregory green
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductReviewJdbcRepository implements ProductReviewRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String findProductReviewByIdSql = """
            select DATA from retail.product_reviews where id = ?
            """;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String updateProductReviewSQL = """
            INSERT INTO retail.product_reviews (id, DATA)
            VALUES (?, to_json(?::json))
            ON CONFLICT (id)
            DO UPDATE SET DATA = to_json(?::json);
            """;

    @Override
    public Optional<ProductReview> findById(String productId) {

        log.info("SQL: {} productId:{} ", findProductReviewByIdSql,productId);
        List<ProductReview> productReviews = jdbcTemplate.query(findProductReviewByIdSql,
                (rs, i) -> {
            var json = rs.getString(1);
                    try {
                        return objectMapper.readValue(json,ProductReview.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }, productId);

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
        var cnt = jdbcTemplate.update(updateProductReviewSQL,ps -> {
            ps.setString(1,productReview.id());
            ps.setString(2,json);
            ps.setString(3,json);
        });

        log.info("Update count: {} for productReview:{}",cnt,productReview);
    }
}
