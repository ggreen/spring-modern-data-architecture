package spring.modern.data.analytics.service.impl;

import spring.modern.data.analytics.consumers.repository.ProductRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.analytics.consumers.service.BroadcastService;
import spring.modern.data.analytics.consumers.service.PromotionRecommendationService;
import spring.modern.data.analytics.consumers.service.impl.CustomerAnalyticsDataService;
import spring.modern.data.domains.customer.*;
import spring.modern.data.domains.customer.order.CustomerOrder;
import spring.modern.data.domains.customer.order.ProductOrder;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerAnalyticsDataServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerAnalyticsDataService subject;

    @Mock
    private PromotionRecommendationService promotionRecommendationService;

    @Mock
    private BroadcastService broadcastService;

    private final static int top3 = 3;
    private CustomerFavorites favorites;
    private final static String id = "id";
    private SortedSet<ProductQuantity> favoriteSet = new TreeSet<>();
    private ProductQuantity productQuantity = JavaBeanGeneratorCreator
                        .of(ProductQuantity.class).create();

    private final static Product expectedProduct = new Product("id","name");
    private final static List<Product> expectedProducts = asList(expectedProduct);
    private final static String expectedId = "id";
    private final static Promotion expected = new Promotion(expectedId,null,expectedProducts);

    private final static Long orderId = 3L;
    private final static String productId = "pId";
    private final static int quantity = 3;
    private final static String customerId = "id";
    private final static ProductOrder productOrder = new ProductOrder(productId,quantity);
    private final static CustomerIdentifier customerIdentifier = new CustomerIdentifier(customerId);
    private final static List<ProductOrder> productOrders = asList(productOrder);
    private final static CustomerOrder customerOrder = new CustomerOrder(orderId,customerIdentifier,productOrders);

    @BeforeEach
    void setUp() {

        favoriteSet.add(productQuantity);
        favorites = new CustomerFavorites(id,favoriteSet);

        subject = new CustomerAnalyticsDataService(
                productRepository,
                top3,
                broadcastService,
                promotionRecommendationService);

    }

    @Test
    void given_customer_when_accept_then_cacheFavorites() {
        var customerId = "u01";

        when(productRepository.findCustomerFavoritesByCustomerIdAndTopCount(anyString(), anyInt())).thenReturn(favorites);
        var customIdentifier = new CustomerIdentifier(customerId);

        subject.constructFavorites(customIdentifier);

        verify(broadcastService).publishCustomerFavorites(any(CustomerFavorites.class));
    }

    @Test
    void given_customerOrder_getRecommendations_based_onOrders_thenPublish_Recommendations() {

        when(productRepository.findFrequentlyBoughtTogether(any())).thenReturn(expectedProducts);
        when(promotionRecommendationService.createPromotion(any(),any())).thenReturn(expected);


        var actual = subject.publishPromotion(customerOrder);

        assertEquals(expected, actual);
        verify(productRepository).findFrequentlyBoughtTogether(customerOrder.productOrders());
        verify(promotionRecommendationService).createPromotion(any(),any());
        verify(broadcastService).publishPromotion(any(Promotion.class));

    }

    @DisplayName("GIVEN No products find sold together WHEN publishPromotion THEN verify no data sent")
    @Test
    void doNotPublishWhenNoRecommendations() {
        var actual = subject.publishPromotion(customerOrder);

        assertNull(actual);

        verify(productRepository).findFrequentlyBoughtTogether(customerOrder.productOrders());
        verify(broadcastService,never()).publishPromotion(any(Promotion.class));

    }
}
