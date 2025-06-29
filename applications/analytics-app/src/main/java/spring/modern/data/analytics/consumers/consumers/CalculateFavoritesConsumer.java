/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.analytics.consumers.consumers;

import spring.modern.data.analytics.consumers.service.CustomerAnalyticService;
import org.springframework.stereotype.Component;
import spring.modern.data.domains.customer.CustomerIdentifier;

import java.util.function.Consumer;

@Component
public record CalculateFavoritesConsumer (CustomerAnalyticService customerAnalyticService)  implements Consumer<CustomerIdentifier> {

    @Override
    public void accept(CustomerIdentifier customerIdentifier) {
        customerAnalyticService.constructFavorites(customerIdentifier);
    }

}
