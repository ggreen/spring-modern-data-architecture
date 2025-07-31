package spring.modern.data.source.transformation;

import lombok.SneakyThrows;
import nyla.solutions.core.io.csv.CsvReader;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.reviews.CustomerReview;
import spring.modern.data.domains.customer.reviews.Sentiment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert for CSV to Customer Reviews
 * @author gregory green
 */
@Component
public class CsvToCustomerReviewsConverter
        implements Converter<String, List<CustomerReview>> {

    private static final int MIN_COLUMNS = 3;

    /**
     * Convert from CSV to lust of customer reviews
     * @param csv the object to convert
     * @return the list of customer reviews
     */
    @SneakyThrows
    @Override
    public List<CustomerReview> convert(String csv) {

        if(csv == null || csv.isEmpty())
            return null;

        var cvsReader = new CsvReader(new StringReader(csv));

        var customerReviews = new ArrayList<CustomerReview>(cvsReader.size());

        for(var rows : cvsReader)
        {
            if(rows.size() < MIN_COLUMNS )
                continue;

            var builder = CustomerReview
                    .builder()
                    .id(rows.get(0))
                    .productId(rows.get(1))
                    .review(rows.get(2));

            if(rows.size() > MIN_COLUMNS)
                builder.sentiment(Sentiment.valueOf(rows.get(3)));

           customerReviews.add(builder.build());
        }

        return customerReviews;
    }
}
