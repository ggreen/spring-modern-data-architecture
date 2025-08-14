

package spring.modern.data.analytics.service.impl;

import spring.modern.data.analytics.entity.CustomerOrderEntity;
import spring.modern.data.analytics.entity.ProductOrderEntity;
import spring.modern.data.analytics.repository.CustomerOrderRepository;
import spring.modern.data.analytics.service.CustomerAnalyticService;
import spring.modern.data.analytics.service.CustomerOrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import spring.modern.data.domains.customer.order.CustomerOrder;

@Service
@AllArgsConstructor
public class CustomerOrderDataService implements CustomerOrderService {

    private final CustomerOrderRepository repository;
    private final CustomerAnalyticService customerAnalyticService;

    @Override
    @Transactional
    public void saveOrder(CustomerOrder customerOrder) {
        repository.saveAll(customerOrder.productOrders().stream()
                .map(po -> CustomerOrderEntity
                        .builder()
                        .orderId(
                                customerOrder.id())
                        .customerId(
                                customerOrder.customerIdentifier().customerId())
                        .productOrder(new ProductOrderEntity(po.productId(), po.quantity())
                        ).build()
                ).toList());

        customerAnalyticService.constructFavorites(customerOrder.customerIdentifier());
        customerAnalyticService.publishPromotion(customerOrder);
    }
}
