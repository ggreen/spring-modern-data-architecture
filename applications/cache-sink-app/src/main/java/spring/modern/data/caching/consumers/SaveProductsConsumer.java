package spring.modern.data.caching.consumers;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public record SaveProductsConsumer(ProductRepository productRepository) implements Consumer<List<Product>> {
    @Override
    public void accept(List<Product> products) {

        log.info("Saving Products: {}",products);
        productRepository.saveAll(products);
    }
}
