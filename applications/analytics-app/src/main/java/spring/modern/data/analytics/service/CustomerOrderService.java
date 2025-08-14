

package spring.modern.data.analytics.service;


import spring.modern.data.domains.customer.order.CustomerOrder;

public interface CustomerOrderService {
    void saveOrder(CustomerOrder customerOrder);
}
