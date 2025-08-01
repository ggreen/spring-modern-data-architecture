

package spring.modern.data.controller;

import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.repository.CustomerFavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadFactory;

/**
 * CustomerFavoritesReactiveController
 *
 * @author Gregory Green
 */
@RestController
@RequestMapping("customer/favorites")
public class SaveCustomerFavoritesController
{

    private final CustomerFavoriteRepository repository;


    private final ThreadFactory factory;


    private static final Logger logger = LoggerFactory.getLogger(SaveCustomerFavoritesController.class);

    public SaveCustomerFavoritesController(CustomerFavoriteRepository repository,
                                           @Qualifier("webSocketThreadFactory")
                                       ThreadFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }


    @PostMapping("favorite")
    public void saveCustomerFavorites(@RequestBody CustomerFavorites customerFavorites)
    {
        repository.save(customerFavorites);
    }
}