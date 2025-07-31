

package spring.modern.data.analytics.consumers.consumers;

import spring.modern.data.analytics.consumers.service.CustomerOrderService;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.order.CustomerOrder;

import java.util.function.Consumer;

    @Component
    public record OrderConsumer
            (CustomerOrderService customerOrderDataService)
            implements Consumer<CustomerOrder> {
        @Override
        public void accept(CustomerOrder customerOrder) {
            customerOrderDataService.saveOrder(customerOrder);

        }
    }
