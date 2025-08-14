package spring.modern.data.analytics.service.ai;

import spring.modern.data.domains.customer.reviews.Sentiment;

/**
 * Determine the sentiment for a given text
 * @author Gregory Green
 */
@FunctionalInterface
public interface SentimentService {
    Sentiment analyze(String text);
}
