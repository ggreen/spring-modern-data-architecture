package spring.modern.data.analytics.service.messaging.amqp;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.reviews.ProductReview;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BroadcastAmqpServiceTest {
    @Mock
    private AmqpTemplate template;

    private BroadcastAmqpService subject;
    private final static Promotion promotion = JavaBeanGeneratorCreator.of(Promotion.class).create();
    private final static CustomerFavorites customerFavorites = JavaBeanGeneratorCreator.of(CustomerFavorites.class).create();
    private final static ProductReview productReview = JavaBeanGeneratorCreator.of(ProductReview.class).create();

    @BeforeEach
    void setUp() {
        subject = new BroadcastAmqpService(template,
                "customerFavExchange",
                "promotionExchange",
                "productReviewExchange");
    }

    @Test
    void sendPromotion() {

        subject.sendPromotion(promotion);

        verify(template).convertAndSend(
                anyString(),
                anyString(),
                any(Promotion.class));
    }

    @Test
    void sendCustomerFavorites() {

        subject.sendCustomerFavorites(customerFavorites);

        verify(template).convertAndSend(
                anyString(),
                anyString(),
                any(CustomerFavorites.class));
    }

    @Test
    void sendProductionReview() {
        subject.sendProductReview(productReview);

        verify(template).convertAndSend(
                anyString(),
                anyString(),
                any(ProductReview.class));
    }
}