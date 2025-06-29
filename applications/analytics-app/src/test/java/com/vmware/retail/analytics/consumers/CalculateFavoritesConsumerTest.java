/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.consumers;

import com.vmware.retail.analytics.service.CustomerAnalyticService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.CustomerIdentifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class CalculateFavoritesConsumerTest {
    @Mock
    private CustomerAnalyticService customerAnalyticService;
    private CalculateFavoritesConsumer subject;

    @BeforeEach
    void setUp() {
        subject = new CalculateFavoritesConsumer(customerAnalyticService);
    }

    @Test
    void given_customer_when_accept_then_cacheFavorites() {
        var customerId = "u01";

        var customIdentifier = new CustomerIdentifier(customerId);

        subject.accept(customIdentifier);

        verify(customerAnalyticService).constructFavorites(any());
    }

}