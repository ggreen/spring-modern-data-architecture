package spring.modern.data.domains.customer.reviews;

import lombok.Builder;

import java.util.SortedSet;

@Builder
public record ProductReview(String id, SortedSet<CustomerReview> customerReviews) {
}
