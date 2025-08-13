package spring.modern.data.analytics.consumers.consumers;

import nyla.solutions.core.patterns.integration.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.analytics.consumers.consumers.ai.SaveProductReviewConsumer;
import spring.modern.data.analytics.consumers.repository.ProductReviewRepository;
import spring.modern.data.analytics.consumers.service.SentimentService;
import spring.modern.data.domains.customer.reviews.CustomerReview;
import spring.modern.data.domains.customer.reviews.ProductReview;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveProductReviewConsumerTest {

    @Mock
    private SentimentService sentimentService;

    @Mock
    private ProductReviewRepository productReviewRepository;

    @Mock
    private Publisher<ProductReview> productReviewBroadcaster;

    private SaveProductReviewConsumer subject;
    private final CustomerReview customerView = CustomerReview.builder().review("review")
            .id("id")
            .productId("product").build();

    @BeforeEach
    void setUp() {
        subject = new SaveProductReviewConsumer(sentimentService,productReviewRepository,productReviewBroadcaster);
    }

    @Test
    void accept() {
        subject.accept(customerView);

        verify(sentimentService).analyze(any());
        verify(productReviewRepository).save(any());
        verify(productReviewBroadcaster).send(any());

    }
}