

package spring.modern.data.source;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.reviews.CustomerReview;

import java.util.List;

@Configuration
@Slf4j
public class AmqpRabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${retail.products.exchange:retail.products}")
    private String productExchange;

    @Value("${source.splitCsv.customer.orders.exchange:retail.customer.orders}")
    private String customerOrderExchange;

    @Value("${retail.product.customer.reviews.destination}")
    private String customerReviewsExchange;

    @Value("${retail.products.routingKey:}")
    private String productsRoutingKey;

    @Value("${retail.product.customer.review.context.destination}")
    private String customerProductReviewContextExchange;

    @Bean
    TopicExchange productExchange(){
        return new TopicExchange(productExchange);
    }

    @Bean
    TopicExchange customerOrderExchange(){
        return new TopicExchange(customerOrderExchange);
    }


    @Bean
    TopicExchange customerReviewsExchange(){
        return new TopicExchange(customerReviewsExchange);
    }

    @Bean
    Publisher<List<Product>> productPublisher(AmqpTemplate amqpTemplate)
    {
        return products -> {
            log.info("Publishing to exchange:{} products:{}",productExchange,products);
            amqpTemplate.convertAndSend(productExchange,productsRoutingKey,products);
        };
    }

    @Bean
    Publisher<CustomerReview> customerReviewPublisher(AmqpTemplate amqpTemplate)
    {
       return customerReview -> {
           log.info("Publishing to exchange:{} reviews:{}",customerReviewsExchange,customerReview);
           amqpTemplate.convertAndSend(customerReviewsExchange,customerReview.id(),customerReview);
       };
    }

    @Bean
    Publisher<String> customerProductReviewContextPublisher(AmqpTemplate amqpTemplate)
    {
        return customerProductReviewContex -> {
            log.info("Publishing to exchange:{} reviews:{}",customerProductReviewContextExchange,customerProductReviewContex);
            amqpTemplate.convertAndSend(customerProductReviewContextExchange,"",customerProductReviewContex);
        };
    }

    @Bean
    ConnectionNameStrategy connectionNameStrategy(){
        return (connectionFactory) -> applicationName;
    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }
}
