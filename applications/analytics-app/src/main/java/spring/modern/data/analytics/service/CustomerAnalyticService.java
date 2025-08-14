

package spring.modern.data.analytics.service;


import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.order.CustomerOrder;

public interface CustomerAnalyticService {
    void constructFavorites(CustomerIdentifier customerIdentifier);

    Promotion publishPromotion(CustomerOrder customerOrder);
}
