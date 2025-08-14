

package spring.modern.data.analytics.repository;


import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.order.ProductOrder;

import java.util.List;

public interface ProductRepository {
    CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId, int topCount);

    List<Product> findFrequentlyBoughtTogether(List<ProductOrder> productOrders);

    void saveProducts(List<Product> products);
}
