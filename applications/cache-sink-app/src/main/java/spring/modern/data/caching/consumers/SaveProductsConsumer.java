package spring.modern.data.caching.consumers;

import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;

import java.util.List;
import java.util.function.Consumer;

/**
 * Sink for saving product doata
 * @author gregory green
 * @param productRepository the repository to save
 */
@Component
public record SaveProductsConsumer(ProductRepository productRepository) implements Consumer<List<Product>> {
    @Override
    public void accept(List<Product> products) {

        productRepository.saveAll(products);
    }
}
