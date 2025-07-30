package spring.modern.data.source.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import spring.modern.data.domains.customer.reviews.CustomerReview;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerReviewControllerTest {

    @Mock
    private AmqpTemplate template;
    private CustomerReview customerReview1;
    private CustomerReview customerReview2;
    private List<CustomerReview> customerReviews;
    private CustomerReviewController subject;

    @BeforeEach
    void setUp() {
        customerReview1 = CustomerReview.builder().id("1").build();
        customerReview2 = CustomerReview.builder().id("2").build();
        customerReviews = List.of(customerReview1,customerReview2);
        subject = new CustomerReviewController(template,"destination");
    }

    @Test
    void loadCustomerReviews() {

        subject.loadCustomerReviews(customerReviews);

        verify(template,times(customerReviews.size())).convertAndSend(anyString(),anyString(),any(CustomerReview.class));

    }
}