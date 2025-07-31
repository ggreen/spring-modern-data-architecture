

package spring.modern.data.domains.customer.order;

import spring.modern.data.domains.customer.CustomerIdentifier;

import java.util.List;


public record CustomerOrder (Long id,
                             CustomerIdentifier customerIdentifier,
                             List<ProductOrder> productOrders) {
}
