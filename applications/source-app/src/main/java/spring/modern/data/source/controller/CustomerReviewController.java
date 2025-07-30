package spring.modern.data.source.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.modern.data.domains.customer.reviews.CustomerReview;

import java.util.List;

/**
 *
 * @param template the AMPQ template
 */
@RestController
@RequestMapping("product/customer/reviews")
public record CustomerReviewController(AmqpTemplate template,
                                       @Value("${retail.product.customer.reviews.destination}")
                                       String exchange) {

    @PostMapping
    public void loadCustomerReviews(List<CustomerReview> customerReviews) {

        customerReviews.stream().forEach( customerReview -> {
            template.convertAndSend(exchange,customerReview.id(),customerReview);
        });
    }



}
