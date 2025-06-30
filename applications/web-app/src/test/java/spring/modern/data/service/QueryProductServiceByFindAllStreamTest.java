package spring.modern.data.service;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryProductServiceByFindAllStreamTest {

    private QueryProductServiceByFindAllStream subject;

    @Mock
    private ProductRepository productRepository;

    private final Product p1 = JavaBeanGeneratorCreator.of(Product.class).create();
    private final Product p2 = JavaBeanGeneratorCreator.of(Product.class).create();
    private final Product p3 = JavaBeanGeneratorCreator.of(Product.class).create();
    private final List<Product> products = List.of(p1,p2,p3);

    @BeforeEach
    void setUp() {
        subject = new QueryProductServiceByFindAllStream(productRepository);
    }

    @Test
    void findByNameContaining(){
        when(productRepository.findAll()).thenReturn(products);

        var actual = subject.findByNameContaining(p2.name());

        List<Product> expected = List.of(p2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByNameContainingNullNoException(){
        var actual = subject.findByNameContaining(null);
        assertThat(actual).isNull();
    }
    @Test
    void findByNameContainingEmptyNoException(){
        var actual = subject.findByNameContaining("");
        assertThat(actual).isNull();
    }

    @Test
    void caseInsensitive() {

        String upperCase = "JOE";
        var product = Product.builder().id("id").name(upperCase).build();
        when(productRepository.findAll()).thenReturn(List.of(product));

        var actual = subject.findByNameContaining(upperCase.toLowerCase());
        assertThat(actual).contains(product);
    }
}