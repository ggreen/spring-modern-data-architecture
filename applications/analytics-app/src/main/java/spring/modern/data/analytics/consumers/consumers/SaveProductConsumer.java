

package spring.modern.data.analytics.consumers.consumers;

import spring.modern.data.analytics.consumers.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.Product;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class SaveProductConsumer
        implements Consumer<List<Product>> {

    private final ProductRepository repository;

    @Override
    public void accept(List<Product> products) {
        repository.saveProducts(products);
    }
}
