/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.analytics.consumers.service;


import spring.modern.data.domains.customer.order.CustomerOrder;

public interface CustomerOrderService {
    void saveOrder(CustomerOrder customerOrder);
}
