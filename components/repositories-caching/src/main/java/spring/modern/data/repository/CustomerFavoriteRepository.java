

package spring.modern.data.repository;

import spring.modern.data.domains.customer.CustomerFavorites;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

/**
 * CustomerFavoriteSortedSetRepository
 *
 * @author Gregory Green
 */
    @Repository
    public interface CustomerFavoriteRepository
            extends KeyValueRepository<CustomerFavorites,String>
    {
    }