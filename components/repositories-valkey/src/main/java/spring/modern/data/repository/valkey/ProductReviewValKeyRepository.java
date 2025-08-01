package spring.modern.data.repository.valkey;

import org.springframework.context.annotation.Profile;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import spring.modern.data.domains.customer.reviews.ProductReview;
import spring.modern.data.repository.ProductReviewRepository;


@Repository
@Profile("valkey")
public interface ProductReviewValKeyRepository extends ProductReviewRepository,KeyValueRepository<ProductReview,String> {
}
