/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.analytics.service;


import spring.modern.data.domains.customer.CustomerIdentifier;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.domains.customer.order.CustomerOrder;

public interface CustomerAnalyticService {
    void constructFavorites(CustomerIdentifier customerIdentifier);

    Promotion publishPromotion(CustomerOrder customerOrder);
}
