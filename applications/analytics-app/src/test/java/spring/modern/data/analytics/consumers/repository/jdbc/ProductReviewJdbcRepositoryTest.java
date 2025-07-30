package spring.modern.data.analytics.consumers.repository.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import spring.modern.data.domains.customer.reviews.CustomerReview;
import spring.modern.data.domains.customer.reviews.ProductReview;
import spring.modern.data.domains.customer.reviews.Sentiment;

import java.util.List;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductReviewJdbcRepositoryTest {

    private ProductReview productReview;
    private ProductReviewJdbcRepository subject;
    private CustomerReview customerReview;
    private final  static String id = "id";
    private final static  String review = "Good";
    private final static String productId= "sku1";
    private final Sentiment sentiment = Sentiment.POSITIVE;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        customerReview = CustomerReview.builder().id(id).review(review).productId(productId).sentiment(sentiment).build();
        productReview = ProductReview.builder().customerReviews(
                new TreeSet<CustomerReview>(List.of(customerReview)))
                .id(id).build();

        subject = new ProductReviewJdbcRepository(jdbcTemplate);
    }

    @Test
    void findById() {
        when(jdbcTemplate.queryForObject(anyString(),any(RowMapper.class),anyString())).thenReturn(productReview);

        var actual = subject.findById(id).orElse(null);

        assertThat(actual).isEqualTo(productReview);
    }

    @Test
    void save() {

        subject.save(productReview);

        verify(jdbcTemplate).update(anyString(),any(PreparedStatementSetter.class));
    }
}