/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.web.analytics.consumers;

import spring.modern.data.analytics.consumers.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.analytics.consumers.consumers.SaveProductConsumer;
import spring.modern.data.domains.customer.Product;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveProductConsumerTest {

    @Mock
    private ProductRepository repository;

    @Test
    void given_products_when_accept_when_save_to_database() {

        var subject = new SaveProductConsumer(repository);
        List<Product> products = asList(new Product("id","name"));

        subject.accept(products);

        verify(repository).saveProducts(any(List.class));


    }
}