

package spring.modern.data.source.controller;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.patterns.integration.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.Product;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private ProductController subject;

    @Mock
    private Publisher<List<Product>> publisher;
    @Mock
    private Converter<String, List<Product>> csvToProductsConverter;
    private List<Product> products = asList(
            JavaBeanGeneratorCreator.of(Product.class).create(),
            JavaBeanGeneratorCreator.of(Product.class).create()
    );

    @BeforeEach
    void setUp() {
        subject = new ProductController(publisher,csvToProductsConverter);
    }

    @Test
    void loadProducts() {
        subject.loadProducts(products);

        verify(publisher).send(any());
    }

    @Test
    void loadProductsCsv() {
        String csv = """
                "sk1","peanut"
                "sk2","jelly""
                """;

        when(csvToProductsConverter.convert(anyString())).thenReturn(products);
        subject.loadProductsCsv(csv);

        verify(publisher).send(any());
    }


}