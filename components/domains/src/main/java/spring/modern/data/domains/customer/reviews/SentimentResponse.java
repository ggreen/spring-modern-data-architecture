package spring.modern.data.domains.customer.reviews;

import lombok.Builder;

@Builder
public record SentimentResponse(Sentiment response) {
}
