package spring.modern.data.caching.consumers;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveProductsConsumerTest {

    private final List<Product> products = new ArrayList<>(JavaBeanGeneratorCreator.of(Product.class).createCollection(2));
    @Mock
    private ProductRepository productRepository;

    private SaveProductsConsumer subject;

    @BeforeEach
    void setUp() {
        subject = new SaveProductsConsumer(productRepository);
    }

    @Test
    void accept() {

        subject.accept(products);

        verify(productRepository).saveAll(any());
    }
}