

package spring.modern.data.analytics.service.impl;

import spring.modern.data.analytics.consumers.repository.CustomerOrderRepository;
import spring.modern.data.analytics.consumers.service.CustomerAnalyticService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.analytics.consumers.service.impl.CustomerOrderDataService;
import spring.modern.data.domains.customer.order.CustomerOrder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerOrderDataServiceTest {
    @Mock
    private CustomerOrderRepository customerOrderRepository;


    @Mock
    private CustomerAnalyticService customerAnalyticService;
    private CustomerOrderDataService subject;

    @BeforeEach
    void setUp() {
        subject = new CustomerOrderDataService(customerOrderRepository,customerAnalyticService);
    }

    @Test
    void saveOrder() {

        var customerOrder = JavaBeanGeneratorCreator.of(CustomerOrder.class).create();

        subject.saveOrder(customerOrder);

        verify(customerOrderRepository).saveAll(any());
        verify(customerAnalyticService).constructFavorites(any());
        verify(customerAnalyticService).publishPromotion(any(CustomerOrder.class));
    }
}