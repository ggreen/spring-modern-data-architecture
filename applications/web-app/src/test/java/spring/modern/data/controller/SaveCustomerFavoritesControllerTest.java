/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.controller;

import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.ProductQuantity;
import spring.modern.data.repository.CustomerFavoriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveCustomerFavoritesControllerTest
{
    @Mock
    private CustomerFavoriteRepository repository;

    @Mock
    private Mono<CustomerFavorites> mono;

    private long expectedQuantity = 54L;
    private TreeSet<ProductQuantity> expectedProductQuantities = new TreeSet<ProductQuantity>();
    private CustomerFavorites expectedCustomerFavorites = new CustomerFavorites("user",expectedProductQuantities);

    private SaveCustomerFavoritesController subject;
    private ThreadFactory factory = Executors.defaultThreadFactory();

    @BeforeEach
    void setUp()
    {
        expectedProductQuantities.add(new ProductQuantity(
                new Product("3L","pname"),
                expectedQuantity));

        subject = new SaveCustomerFavoritesController(repository,
                factory);
    }


    @Test
    void given_favorites_whenSave_saveOnRepository()
    {
        subject.saveCustomerFavorites(expectedCustomerFavorites);
        verify(repository).save(any());
    }
}