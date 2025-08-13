package spring.modern.data.analytics.consumers.service;

import org.springframework.stereotype.Service;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;

import java.util.List;

@Service
@FunctionalInterface
public interface PromotionRecommendationService {

    Promotion createPromotion(CustomerIdentifier customerIdentifier, List<Product> recommends);
}
