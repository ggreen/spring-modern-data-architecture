

package spring.modern.data.analytics.repository;

import spring.modern.data.analytics.entity.CustomerOrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface CustomerOrderRepository
            extends CrudRepository<CustomerOrderEntity,Long> {
    }

