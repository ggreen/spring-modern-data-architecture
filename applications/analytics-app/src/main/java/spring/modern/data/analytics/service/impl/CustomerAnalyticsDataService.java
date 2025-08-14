

package spring.modern.data.analytics.service.impl;

import spring.modern.data.analytics.repository.ProductRepository;
import spring.modern.data.analytics.service.messaging.BroadcastService;
import spring.modern.data.analytics.service.CustomerAnalyticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import spring.modern.data.analytics.service.ai.AiService;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.order.CustomerOrder;

@Service
@Slf4j
public class CustomerAnalyticsDataService implements CustomerAnalyticService {
//    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;
    private final int topCount;
    private final BroadcastService broadcastService;
    private final AiService promotionRecommendationService;

//    private final String customerFavoritesExchange;
//    private final String promotionExchange;

    /*
     @Value("${retail.customer.promotions.exchange:retail.customer.promotions}")
     String promotionExchange
     @Value("${retail.customer.favorites.exchange:retail.customer.favorites}")
                                        String customerFavoritesExchange,

                                        this.promotionExchange = promotionExchange

     */
    public CustomerAnalyticsDataService(
            ProductRepository productRepository,
            @Value("${retail.favorites.top.count}") int topCount,
            BroadcastService broadcastService, AiService aiService)
    {
        this.productRepository = productRepository;
        this.topCount = topCount;
        this.broadcastService = broadcastService;
        this.promotionRecommendationService  = aiService;
    }

    @Async
    @Override
    public void constructFavorites(CustomerIdentifier customerIdentifier) {
        log.info("Finding favorites for customer:{}",customerIdentifier);

        var customerId = customerIdentifier.customerId();
        var customerFavorites = productRepository.findCustomerFavoritesByCustomerIdAndTopCount(customerId, topCount);

        log.info("Sending customerFavorites: {}",customerFavorites);
        this.broadcastService.sendCustomerFavorites(customerFavorites);
    }

    /**
     * Determine products frequently both together and send
     * @param customerOrder the customer order to publish
     * @return the promotion
     */
    @Async
    @Override
    public Promotion publishPromotion(CustomerOrder customerOrder) {

        //You are a marketing expert, create a short marketingMessaging less than 70 characters for the product for the product Jelly to a person named nyla
        var recommendations = this.productRepository.findFrequentlyBoughtTogether(customerOrder.productOrders());
        log.info("recommendations: {} for customerOrder: {}",recommendations,customerOrder);

        if(recommendations == null || recommendations.isEmpty()) {
            log.info("No recommendations found");
            return null;
        }


        var customerId = customerOrder.customerIdentifier().customerId();

        var promotion = promotionRecommendationService.createPromotion(
                customerOrder.customerIdentifier(),
                recommendations);

        log.info("Publishing promotion : {}",promotion);
        this.broadcastService.sendPromotion(promotion);

        return promotion;

    }
}
