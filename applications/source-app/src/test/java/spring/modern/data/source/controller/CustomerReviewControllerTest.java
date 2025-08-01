package spring.modern.data.source.controller;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.integration.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.reviews.CustomerReview;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerReviewControllerTest {

    @Mock
    private Publisher<CustomerReview> customerReviewPublisher;
    @Mock
    private Converter<String,List<CustomerReview>> converter;

    private CustomerReview customerReview1;
    private CustomerReview customerReview2;
    private List<CustomerReview> customerReviews;
    private CustomerReviewController subject;
    private final String csv = """
      "id","productId","review","NEGATIVE"
    """;

    @BeforeEach
    void setUp() {
        customerReview1 = CustomerReview.builder().id("1").build();
        customerReview2 = CustomerReview.builder().id("2").build();
        customerReviews = List.of(customerReview1,customerReview2);
        subject = new CustomerReviewController(customerReviewPublisher,converter);
    }

    @Test
    void loadCustomerReviews() {

        subject.loadCustomerReviews(customerReviews);
        verify(customerReviewPublisher,times(customerReviews.size())).send(any());

    }

    @Test
    void loadCustomerReviewersCsv() {

        subject.loadCustomerReviewsCsv(csv);

        verify(converter).convert(anyString());

    }
}