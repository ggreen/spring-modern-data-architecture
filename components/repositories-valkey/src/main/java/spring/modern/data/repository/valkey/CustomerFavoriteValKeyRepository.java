package spring.modern.data.repository.valkey;

import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.repository.CustomerFavoriteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("valkey")
public interface CustomerFavoriteValKeyRepository  extends CustomerFavoriteRepository, KeyValueRepository<CustomerFavorites,String> {
}
