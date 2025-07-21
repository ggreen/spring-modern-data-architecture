package spring.modern.data.repository.valkey;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import spring.modern.data.domains.customer.reviews.ProductReview;

@Repository
public interface ProductReviewRepository extends KeyValueRepository<ProductReview,String> {
}
