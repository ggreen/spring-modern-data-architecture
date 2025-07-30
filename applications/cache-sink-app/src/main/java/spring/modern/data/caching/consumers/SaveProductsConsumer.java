package spring.modern.data.caching.consumers;

import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;

import java.util.List;
import java.util.function.Consumer;

@Component
public record SaveProductsConsumer(ProductRepository productRepository) implements Consumer<List<Product>> {
    @Override
    public void accept(List<Product> products) {

        productRepository.saveAll(products);
    }
}
