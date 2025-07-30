package spring.modern.data.analytics.consumers;

import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.modern.data.domains.customer.reviews.ProductReview;

@Configuration
public class ProductReviewBroadcasterConfig {
    @Value("${spring.cloud.stream.bindings.saveProductReviewConsumer-out-0.destination:retail.product.review.customer.output}")
    private String productReviewTopicExchange;

    @Bean
    Publisher<ProductReview> broadcaster(AmqpTemplate template)
    {
        return productReview -> template.convertAndSend(productReviewTopicExchange, productReview.id(), productReview);
    }
}
