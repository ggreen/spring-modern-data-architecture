package spring.modern.data.domains.customer.reviews;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class CustomerReviewTest {

    @Test
    void comparingTo() {
        var c1 = new CustomerReview("b", "jelly","review",Sentiment.NEGATIVE);
        var c2 = new CustomerReview("a", "jelly","review",Sentiment.NEGATIVE);

        assertTrue(c1.compareTo(c2) > 0);

    }

    @Test
    void comparingEqual() {
        var c1 = new CustomerReview("a", "jelly","review",Sentiment.NEGATIVE);
        var c2 = new CustomerReview("a", "jelly","review",Sentiment.NEGATIVE);

        assertTrue(c1.compareTo(c2) == 0);
    }

    @Test
    void comparingEqualNull() {
        var c1 = new CustomerReview("a", "jelly","review",Sentiment.NEGATIVE);

        assertTrue(c1.compareTo(null) > 0);

    }
}