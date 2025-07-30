/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.controller;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.reviews.CustomerReview;
import spring.modern.data.domains.customer.reviews.ProductReview;
import spring.modern.data.domains.customer.reviews.ProductReviewSummary;
import spring.modern.data.domains.customer.reviews.Sentiment;
import spring.modern.data.repository.ProductRepository;
import spring.modern.data.repository.ProductReviewRepository;
import spring.modern.data.service.QueryProductService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest
{

    @Mock
    private ProductRepository repository;

    @Mock
    QueryProductService queryProductService;

    @Mock
    private ProductReviewRepository productReviewRepository;

    private final Product product = JavaBeanGeneratorCreator.of(Product.class).create();
    private final String name = "Pistachios";
    private final Product[] products = {product};
    private final String custId= "01";
    private final String review = """
            I LOVE SPRING!!!
            """;
    private final String productId = "jelly";
    private final CustomerReview customerReview = new CustomerReview(custId,productId, review, Sentiment.NEGATIVE);
    private ProductReview productReview;
    private ProductController subject;

    @BeforeEach
    void setUp() {
        subject = new ProductController(repository,queryProductService,productReviewRepository);

        productReview = ProductReview.builder().id(product.id()).customerReviews(new TreeSet<>(Set.of(customerReview))).build();
    }


    @Test
    void getProductReviewSummariesByNameContaining() {

        ProductReviewSummary productReviewSummary = ProductReviewSummary.builder()
                .id(product.id())
                .productReview(productReview).product(product).build();
        List<ProductReviewSummary> expected = List.of(productReviewSummary);


        when(queryProductService.findByNameContaining(anyString())).thenReturn(List.of(product));
        when(productReviewRepository.findById(anyString())).thenReturn(Optional.of(productReview));

        var actual = subject.getProductReviewsByNameContaining(productReviewSummary.product().name());

        assertThat(actual).isEqualTo(expected);

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

    @Test
    void saveProductReview() {

        subject.saveProductReview(productReview);

        verify(productReviewRepository).save(any());
    }

    @Test
    void getProductReviewSummary() {

        when(productReviewRepository.findById(any())).thenReturn(Optional.of(productReview));
        when(repository.findById(anyString())).thenReturn(Optional.of(product));


        var expected = ProductReviewSummary.builder()
                .id(product.id())
                .product(product)
                .productReview(productReview)
                .build();

        var actual = subject.getProductReviewSummary(product.id());

        assertThat(actual).isEqualTo(expected);
    }
}