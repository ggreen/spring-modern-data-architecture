package spring.modern.data.source.transformation;

import nyla.solutions.core.util.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spring.modern.data.domains.customer.reviews.CustomerReview;
import spring.modern.data.domains.customer.reviews.Sentiment;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class CsvToCustomerReviewsConverterTest {

    private CsvToCustomerReviewsConverter subject;

    private final CustomerReview customerReview = CustomerReview.builder()
            .id("id")
            .productId("productId")
            .review("review")
            .sentiment(Sentiment.NEGATIVE)
            .build();
    private final String csv = """
            "${id}", "${productId}", "${review}", "${sentiment}"
            """;

    private final String csv_no_sentiment = """
            "${id}", "${productId}", "${review}"
            """;

    @BeforeEach
    void setUp() {
        subject = new CsvToCustomerReviewsConverter();
    }

    @Test
    void convert() {
        List<CustomerReview> expected = List.of(customerReview);

        String reviewsCsv = Text.formatText(csv,
                Map.of("id",customerReview.id(),
                        "productId",customerReview.productId(),
                        "review",customerReview.review(),
                        "sentiment",customerReview.sentiment()
                                .toString()));

        var actual = subject.convert(reviewsCsv);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void convert_no_sentiment() {
        List<CustomerReview> expected = List.of(CustomerReview
                .builder()
                        .id(customerReview.id())
                    .productId(customerReview.productId())
                    .review(customerReview.review())
                .build());

        var reviewsCsvNoSentiment = Text.formatText(csv_no_sentiment,
                Map.of("id",customerReview.id(),
                        "productId",customerReview.productId(),
                        "review",customerReview.review()));

        var actual = subject.convert(reviewsCsvNoSentiment);

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void convert_with_bank() {
        assertThat(subject.convert("")).isNull();
    }

    @Test
    void convert_with_null() {
        assertThat(subject.convert(null)).isNull();
    }

    @Test
    void convert_not_enough_fields() {
        String invalid = """
                "id","product"
                """;
        assertThat(subject.convert(invalid))
                .isEmpty();
    }
}