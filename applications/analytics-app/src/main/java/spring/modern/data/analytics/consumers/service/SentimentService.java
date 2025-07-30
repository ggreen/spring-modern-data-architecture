package spring.modern.data.analytics.consumers.service;

import spring.modern.data.domains.customer.reviews.Sentiment;

@FunctionalInterface
public interface SentimentService {
    Sentiment analyze(String text);
}
