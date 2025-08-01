package spring.modern.data.domains.customer.reviews;

import lombok.Builder;
import spring.modern.data.domains.customer.Product;

@Builder
public record ProductReviewSummary(String id, Product product, ProductReview productReview) {
}
