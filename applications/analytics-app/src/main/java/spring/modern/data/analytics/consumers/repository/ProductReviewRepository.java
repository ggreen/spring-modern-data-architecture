package spring.modern.data.analytics.consumers.repository;

import spring.modern.data.domains.customer.reviews.ProductReview;

import java.util.Optional;

public interface ProductReviewRepository {
    Optional<ProductReview> findById(String productId);

    void save(ProductReview productReview);
}
