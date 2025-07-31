

package spring.modern.data.caching.consumers;

import spring.modern.data.caching.consumers.SaveFavoritesConsumer;
import spring.modern.data.repository.CustomerFavoriteRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.ProductQuantity;

import java.util.TreeSet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveFavoritesConsumerTest {
    @Mock
    private CustomerFavoriteRepository customerAnalyticRepository;
    private SaveFavoritesConsumer subject;

    @BeforeEach
    void setUp() {
        subject = new SaveFavoritesConsumer(customerAnalyticRepository);
    }

    @Test
    void given_customer_when_accept_then_cacheFavorites() {
        String id = "u01";

        var productQuantities = new TreeSet<ProductQuantity>();
        var productQuantity = JavaBeanGeneratorCreator.of(ProductQuantity.class).create();

        productQuantities.add(productQuantity);
        var customIdentifier = new CustomerFavorites(id,productQuantities);

        subject.accept(customIdentifier);

        verify(customerAnalyticRepository).save(any(CustomerFavorites.class));
    }
}