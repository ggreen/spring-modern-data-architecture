package spring.modern.data.analytics.consumers.consumers.ai;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.analytics.consumers.repository.ProductReviewRepository;
import spring.modern.data.analytics.consumers.service.SentimentService;
import spring.modern.data.analytics.consumers.service.messaging.BroadcastService;
import spring.modern.data.domains.customer.reviews.CustomerReview;
import spring.modern.data.domains.customer.reviews.ProductReview;

import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveProductReviewConsumerTest {

    @Mock
    private SentimentService sentimentService;

    @Mock
    private ProductReviewRepository productReviewRepository;

    @Mock
    private BroadcastService productReviewBroadcaster;

    private SaveProductReviewConsumer subject;
    private final CustomerReview customerView = CustomerReview.builder().review("review")
            .id("id")
            .productId("product").build();
    private final static String productId = "productId";

    @BeforeEach
    void setUp() {
        subject = new SaveProductReviewConsumer(sentimentService,productReviewRepository,productReviewBroadcaster);
    }

    @Test
    void accept() {
        subject.accept(customerView);

        verify(sentimentService).analyze(any());
        verify(productReviewRepository).save(any());
        verify(productReviewBroadcaster).sendProductReview(any());

    }

    @Test
    void givenDuplicateCustomerReviewWhenMergeRemovePrevious() {

        var oldReviewText = "old";
        var prevReview= CustomerReview.builder().id(productId)
                .review(oldReviewText).build();

        var newReviewText= "new";
        var newReview= CustomerReview.builder().id(productId)
                .review(newReviewText).build();

        var productReview = ProductReview.builder()
                .customerReviews(new TreeSet<>(List.of(prevReview))).build();

        productReview = subject.addCustomerReview(newReview,productReview);

        assertEquals(1, productReview.customerReviews().size());
        assertTrue(productReview.customerReviews().contains(newReview));

    }
}