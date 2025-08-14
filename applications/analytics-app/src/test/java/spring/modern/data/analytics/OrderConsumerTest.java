

package spring.modern.data.analytics;

import spring.modern.data.analytics.service.CustomerOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.analytics.consumers.OrderConsumer;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.order.CustomerOrder;
import spring.modern.data.domains.customer.order.ProductOrder;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderConsumerTest {

    @Mock
    private CustomerOrderService customerOrderService;

    @Test
    void given_order_when_accepted_then_save_to_databases() {

       var subject = new OrderConsumer(customerOrderService);
        Long id = 3L;
        String customerId =  "u4L";
        Long[] productIds = {3L};

        List<ProductOrder> productOrders = asList(new ProductOrder("1L",3),
                new ProductOrder("2L",2));

        var customerOrder = new CustomerOrder(id,new CustomerIdentifier(customerId),productOrders);
        subject.accept(customerOrder);

        verify(customerOrderService).saveOrder(any());
    }
}