package spring.modern.data.analytics.service.ai;

import org.springframework.stereotype.Service;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;

import java.util.List;

@Service
public interface AiService extends SentimentService {

    Promotion createPromotion(CustomerIdentifier customerIdentifier, List<Product> recommends);
}
