

package spring.modern.data.caching.consumers;

import spring.modern.data.repository.CustomerFavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.CustomerFavorites;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveFavoritesConsumer  implements Consumer<CustomerFavorites> {

    private final CustomerFavoriteRepository customerFavoriteRepository;

    @Override
    public void accept(CustomerFavorites customerFavorites) {

        log.info("Saving favorites: {}",customerFavorites);

        customerFavoriteRepository.save(customerFavorites);
    }

}
