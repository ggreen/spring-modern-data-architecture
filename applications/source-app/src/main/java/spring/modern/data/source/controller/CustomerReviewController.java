package spring.modern.data.source.controller;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.modern.data.domains.customer.reviews.CustomerReview;

import java.util.List;

/**
 * @param customerReviewPublisher  the AMPQ publisher
 * @param converter converter from CSV to list
 */
@RestController
@RequestMapping("product/customer/reviews")
public record CustomerReviewController(Publisher<CustomerReview> customerReviewPublisher,
                                       Converter<String, List<CustomerReview>> converter
                                       ) {

    @PostMapping
    public void loadCustomerReviews(@RequestBody List<CustomerReview> customerReviews) {

        customerReviews.forEach(customerReviewPublisher::send);
    }

    @PostMapping("csv")
    public void loadCustomerReviewsCsv(@RequestBody String csv) {
        var customerReviews = converter.convert(csv);
        if(customerReviews == null)
            return;

        this.loadCustomerReviews(customerReviews);

    }
}
