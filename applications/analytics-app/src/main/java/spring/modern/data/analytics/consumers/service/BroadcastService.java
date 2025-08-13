package spring.modern.data.analytics.consumers.service;

import org.springframework.stereotype.Service;
import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Promotion;

@Service
public class BroadcastService {
    public void publishCustomerFavorites(CustomerFavorites customerFavorites) {

    }

    public void publishPromotion(Promotion promotion) {

    }
}
