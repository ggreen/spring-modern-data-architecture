package spring.modern.data.source.controller;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.modern.data.domains.customer.reviews.CustomerReview;

import java.util.List;

/**
 * @param template  the AMPQ template
 * @param converter converter from CSV to list
 */
@RestController
@RequestMapping("product/customer/reviews")
public record CustomerReviewController(AmqpTemplate template,
                                       Converter<String, List<CustomerReview>> converter,
                                       @Value("${retail.product.customer.reviews.destination}")
                                       String exchange) {

    @PostMapping
    public void loadCustomerReviews(@RequestBody List<CustomerReview> customerReviews) {

        customerReviews.stream().forEach( customerReview -> {
            template.convertAndSend(exchange,customerReview.id(),customerReview);
        });
    }

    @PostMapping("csv")
    public void loadCustomerReviewsCsv(@RequestBody String csv) {
        var customerReviews = converter.convert(csv);
        if(customerReviews == null)
            return;

        this.loadCustomerReviews(customerReviews);

    }
}
