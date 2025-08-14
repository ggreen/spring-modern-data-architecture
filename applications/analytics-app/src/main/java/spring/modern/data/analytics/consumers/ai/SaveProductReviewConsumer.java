package spring.modern.data.analytics.consumers.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.modern.data.analytics.repository.ProductReviewRepository;
import spring.modern.data.analytics.service.ai.SentimentService;
import spring.modern.data.analytics.service.messaging.BroadcastService;
import spring.modern.data.domains.customer.reviews.CustomerReview;
import spring.modern.data.domains.customer.reviews.ProductReview;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * Save the product review to the repository
 * @author gregory green
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SaveProductReviewConsumer implements Consumer<CustomerReview> {

    private final SentimentService sentimentService;
    private final ProductReviewRepository productReviewRepository;
    private final BroadcastService broadcaster;

    /**
     * Saves the customer review
     * @param customerReview the provided customer review
     */
    @Override
    public void accept(CustomerReview customerReview) {

        log.info("Received customerReview: {}",customerReview);

        if(customerReview.sentiment() == null)
        {
            log.info("Analyzing sentiment");
            var sentiment = sentimentService.analyze(customerReview.review());

            log.info("sentiment: {}",sentiment);

            // Build s
            customerReview = CustomerReview.builder()
                    .id(customerReview.id())
                    .review(customerReview.review())
                    .productId(customerReview.productId())
                    .sentiment(sentiment)
                    .build();

            log.info("New customerReview with sentiment: {}",customerReview);
        }

        var productReview = productReviewRepository.findById(customerReview.productId())
                .orElse(ProductReview.builder().id(customerReview.productId())
                        .customerReviews(new TreeSet<>()).build());


        log.info("productReview: {}",productReview);

        productReview = addCustomerReview(customerReview, productReview);

        productReviewRepository.save(productReview);

        log.info("Saved: {}",productReview);

        broadcaster.sendProductReview(productReview); //broadcast latest changes

    }

    ProductReview addCustomerReview(CustomerReview customerReview, ProductReview productReview) {

        var customerReviews = new ArrayList<>(
                productReview.customerReviews()
                .stream()
                        .filter( oldReview -> !oldReview.id().equals(customerReview.id()))
                                .toList());
        customerReviews.add(customerReview);

        return ProductReview.builder().id(productReview.id()).customerReviews(
                new TreeSet<>(customerReviews))
                .build();
    }
}
