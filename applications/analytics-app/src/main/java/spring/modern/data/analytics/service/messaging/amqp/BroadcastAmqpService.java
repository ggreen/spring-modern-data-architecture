package spring.modern.data.analytics.service.messaging.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.modern.data.analytics.service.messaging.BroadcastService;
import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.reviews.ProductReview;

@Service
@Slf4j
public record BroadcastAmqpService(
        AmqpTemplate template,

        @Value("${retail.customer.favorites.exchange:retail.customer.favorites}")
        String customerFavoritesExchange,

        @Value("${retail.customer.promotions.exchange:retail.customer.promotions}")
        String promotionExchange,

        @Value("${spring.cloud.stream.bindings.saveProductReviewConsumer-out-0.destination:retail.product.review.customer.output}")
        String productReviewTopicExchange
        ) implements BroadcastService {

    /**
     * Publish to customerFavoritesExchange
     * @param customerFavorites the customer favorites to sned
     */
    public void sendCustomerFavorites(CustomerFavorites customerFavorites) {

        log.info("Publishing: {} to {}",customerFavoritesExchange,customerFavorites);
        this.template.convertAndSend(customerFavoritesExchange,customerFavorites.getId(),customerFavorites);
        log.info("Published: {} to {}",customerFavoritesExchange,customerFavorites);

    }

    /**
     * Send to the promotionExchange
     * @param promotion the promtion to send
     */
    public void sendPromotion(Promotion promotion) {

        log.info("Publishing: {} to {}",promotionExchange,promotion);
        this.template.convertAndSend(promotionExchange,promotion.id(),promotion);

        log.info("Published: {} to {}",promotionExchange,promotion);
    }

    @Override
    public void sendProductReview(ProductReview productReview) {

        log.info("Publishing: {} to {}",productReview,productReviewTopicExchange);

        template.convertAndSend(productReviewTopicExchange, productReview.id(), productReview);

        log.info("Published: {} to {}",productReview,productReviewTopicExchange);
    }
}
