/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.analytics.consumers.service.impl;

import spring.modern.data.analytics.consumers.repository.ProductRepository;
import spring.modern.data.analytics.consumers.service.CustomerAnalyticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.order.CustomerOrder;

@Service
@Slf4j
public class CustomerAnalyticsDataService implements CustomerAnalyticService {
    private final RabbitTemplate rabbitTemplate;
    private final ProductRepository productRepository;
    private final int topCount;

    private final String customerFavoritesExchange;
    private final String promotionExchange;

    public CustomerAnalyticsDataService(RabbitTemplate rabbitTemplate,
                                        ProductRepository productRepository,
                                        @Value("${retail.favorites.top.count}") int topCount,
                                        @Value("${retail.customer.favorites.exchange:retail.customer.favorites}")
                                        String customerFavoritesExchange,
                                        @Value("${retail.customer.promotions.exchange:retail.customer.promotions}")
                                        String promotionExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.productRepository = productRepository;
        this.topCount = topCount;
        this.customerFavoritesExchange = customerFavoritesExchange;
        this.promotionExchange = promotionExchange;
    }


    @Async
    @Override
    public void constructFavorites(CustomerIdentifier customerIdentifier) {
        log.info("Finding favorites for customer:{}",customerIdentifier);

        var customerId = customerIdentifier.customerId();
        var customerFavorites = productRepository.findCustomerFavoritesByCustomerIdAndTopCount(customerId, topCount);

        log.info("Sending customerFavorites: {}",customerFavorites);
        this.rabbitTemplate.convertAndSend(customerFavoritesExchange,customerIdentifier.customerId(),customerFavorites);
    }

    /**
     * Determine products frequently both together and send
     * @param customerOrder
     * @return
     */
    @Async
    @Override
    public Promotion publishPromotion(CustomerOrder customerOrder) {
        var recommendations = this.productRepository.findFrequentlyBoughtTogether(customerOrder.productOrders());
        log.info("recommendations: {} for customerOrder: {}",recommendations,customerOrder);

        if(recommendations == null || recommendations.isEmpty()) {
            log.info("No recommendations found");
            return null;
        }
        var customerId = customerOrder.customerIdentifier().customerId();
        var promotion = new Promotion(customerId,
                null,recommendations);

        log.info("Publishing promotion : {}",promotion);
        this.rabbitTemplate.convertAndSend(promotionExchange,customerId,promotion);

        return promotion;

    }
}
