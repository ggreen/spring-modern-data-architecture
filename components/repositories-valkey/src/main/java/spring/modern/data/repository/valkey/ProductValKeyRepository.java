package spring.modern.data.repository.valkey;

import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("valkey")
public interface ProductValKeyRepository  extends ProductRepository, KeyValueRepository<Product,String> {
}
