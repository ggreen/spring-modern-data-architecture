

package spring.modern.data.source.controller;

import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.reviews.CustomerReview;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {

    private final Publisher<List<Product>> productPublisher;
    private final Converter<String,List<Product>> csvToProducts;

    @PostMapping
    public void loadProducts(@RequestBody List<Product> products) {
        productPublisher.send(products);
    }

    @PostMapping
    @RequestMapping("csv")
    public void loadProductsCsv(@RequestBody String csv) {
        loadProducts(csvToProducts.convert(csv));
    }


}
