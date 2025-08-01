package spring.modern.data.caching.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.reviews.ProductReview;
import spring.modern.data.repository.ProductReviewRepository;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveProductReviewConsumer implements Consumer<ProductReview> {
    private final ProductReviewRepository productReviewRepository;

    @Override
    public void accept(ProductReview productReview) {

        log.info("Saving productReview: {}",productReview);
        productReviewRepository.save(productReview);
    }
}
