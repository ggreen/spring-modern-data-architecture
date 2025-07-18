/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.controller;

import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.service.QueryProductService;

import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest
{

    @Mock
    private ProductRepository repository;

    @Mock
    QueryProductService queryProductService;

    private Product product = JavaBeanGeneratorCreator.of(Product.class).create();
    private ProductController subject;
    private String name = "Pistachios";
    private Product[] products = {product};

    @BeforeEach
    void setUp() {
        subject = new ProductController(repository,queryProductService);
    }

    @Test
    void findByNameContainingWhenNameNull() {
        assertDoesNotThrow(()-> subject.getProductsByNameContaining(null));
    }

    @Test
    void findByNameContaining() {
        var expected = asList(product);
        when(queryProductService.findByNameContaining(anyString())).thenReturn(expected);
        var actual = subject.getProductsByNameContaining(name);

        assertEquals(expected, actual);
    }

    @Test
    void given_product_When_saveProduct_Then_GetProduct_Equals_Saved()
    {
        when(repository.findById(anyString())).thenReturn(Optional.of(product));

        subject.saveProduct(product);

        assertEquals(product,subject.getProductById(product.id()));
        verify(repository).save(any());
    }

    @Test
    void saveProducts() {

        subject.saveProducts(products);

        verify(repository).save(any());
    }
}