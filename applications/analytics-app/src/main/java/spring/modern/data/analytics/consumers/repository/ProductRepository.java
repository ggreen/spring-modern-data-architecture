/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.analytics.consumers.repository;


import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.order.ProductOrder;

import java.util.List;

public interface ProductRepository {
    CustomerFavorites findCustomerFavoritesByCustomerIdAndTopCount(String customerId, int topCount);

    List<Product> findFrequentlyBoughtTogether(List<ProductOrder> productOrders);

    void saveProducts(List<Product> products);
}
