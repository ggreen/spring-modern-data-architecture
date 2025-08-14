package spring.modern.data.analytics;

import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.rabbitmq.username:guest}")
    private String username = "guest";

    @Value("${spring.rabbitmq.password:guest}")
    private String password   = "guest";

    @Value("${spring.rabbitmq.host:127.0.0.1}")
    private String hostname = "localhost";

    @Value("${spring.cloud.stream.bindings.saveProductConsumer-in-0.destination}.${spring.cloud.stream.bindings.saveProductConsumer-in-0.group}")
    private String productStreamName;


    @Value("${retail.customer.favorites.exchange:retail.customer.favorites}")
    private String customerFavoritesExchangeName;

    @Value("${retail.customer.promotions.exchange:retail.customer.promotions}")
    private String promotionExchangeName;

    @Value("${spring.cloud.stream.bindings.saveProductReviewConsumer-out-0.destination}")
    private String productViewCustomerExchangeName;

    @Bean
    public Exchange customerFavoritesExchange()
    {
        return new TopicExchange(customerFavoritesExchangeName);
    }

    @Bean
    public Exchange promotionExchange()
    {
        return new TopicExchange(promotionExchangeName);
    }

    //retail.product.review.customer.output
    @Bean
    public Exchange productViewCustomer()
    {
        return new TopicExchange(productViewCustomerExchangeName);
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


    @Bean
    @Profile("!rabbit-product-quorum")
    Queue productStream() {
        return QueueBuilder.durable(productStreamName)
                .stream().build();
    }


    @Bean
    @ConditionalOnProperty(name = "rabbitmq.streaming.replay",havingValue = "true")
    RabbitListenerContainerFactory<StreamListenerContainer> nativeFactory(Environment env) {

        log.info("***Replaying, setting offset to first the record for streams");
        var factory = new StreamRabbitListenerContainerFactory(env);
        factory.setNativeListener(true);

        factory.setConsumerCustomizer((id, builder) -> {
            log.info("**** Setting offset to FIRST");
            builder.offset(OffsetSpecification.first());
        });

        return factory;
    }


}
