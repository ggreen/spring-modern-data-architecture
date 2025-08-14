package spring.modern.data.analytics.service.ai;

import spring.modern.data.domains.customer.reviews.Sentiment;

@FunctionalInterface
public interface SentimentService {
    Sentiment analyze(String text);
}
