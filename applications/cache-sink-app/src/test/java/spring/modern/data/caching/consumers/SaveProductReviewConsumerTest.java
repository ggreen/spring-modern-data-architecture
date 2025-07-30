package spring.modern.data.caching.consumers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.reviews.ProductReview;
import spring.modern.data.repository.ProductReviewRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveProductReviewConsumerTest {

    private ProductReview productReview;
    private SaveProductReviewConsumer subject;
    @Mock
    private ProductReviewRepository productReviewRepository;

    @BeforeEach
    void setUp() {
        productReview = ProductReview.builder().build();
        subject = new SaveProductReviewConsumer(productReviewRepository);
    }

    @Test
    void accept() {
        subject.accept(productReview);

        verify(productReviewRepository).save(any());
    }
}