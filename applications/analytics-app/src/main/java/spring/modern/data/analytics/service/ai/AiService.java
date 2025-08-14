package spring.modern.data.analytics.service.ai;

import org.springframework.stereotype.Service;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;

import java.util.List;

/**
 * Artificial Intelligence based services
 * @author gregory green
 */
@Service
public interface AiService extends SentimentService {

    /**
     *
     * @param customerIdentifier the customer identifier
     * @param recommends the list of product recommendations
     * @return the promotion
     */
    Promotion createPromotion(CustomerIdentifier customerIdentifier, List<Product> recommends);
}
