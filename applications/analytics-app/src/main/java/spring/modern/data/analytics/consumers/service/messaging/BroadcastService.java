package spring.modern.data.analytics.consumers.service.messaging;

import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.reviews.ProductReview;

/**
 * Facade for sending messaging
 * @author gregory green
 */
public interface BroadcastService {

    void sendCustomerFavorites(CustomerFavorites customerFavorites);
    void sendPromotion(Promotion promotion);
    void sendProductReview(ProductReview productReview);
}
