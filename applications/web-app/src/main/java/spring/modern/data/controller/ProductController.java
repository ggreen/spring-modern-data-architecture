/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.controller;

import lombok.RequiredArgsConstructor;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.reviews.ProductReview;
import spring.modern.data.domains.customer.reviews.ProductReviewSummary;
import spring.modern.data.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spring.modern.data.repository.valkey.ProductReviewRepository;
import spring.modern.data.service.QueryProductService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ProductController for product information REST access
 *
 * @author Gregory Green
 */
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@Slf4j
public class ProductController
{
    private final ProductRepository repository;
    private final QueryProductService queryProductService;
    private final ProductReviewRepository productReviewRepository;

    @PostMapping("product")
    public void saveProduct(@RequestBody Product product)
    {
        log.info("Saving product: {}",product);

        repository.save(product);
    }


    @GetMapping("product/{id}")
    public Product getProductById(@PathVariable String id)
    {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("product/name/{name}")
    public List<Product> getProductsByNameContaining(@PathVariable String name) {
        return queryProductService.findByNameContaining(name);
    }

    @PostMapping
    public void saveProducts(@RequestBody Product[] products) {
        for (Product product: products)
            saveProduct(product);
    }

    /**
     * Save review to repository
     * @param productReview the product review
     */
    @PostMapping("product/review")
    public void saveProductReview(@RequestBody ProductReview productReview) {
        this.productReviewRepository.save(productReview);

    }

    @GetMapping("product/review/{id}")
    public ProductReviewSummary getProductReviewSummary(@PathVariable String id) {
        var result = this.productReviewRepository.findById(id);

        if(result.isEmpty())
            return null;

        var productReview = result.get();

        return ProductReviewSummary.builder().id(id)
                .productReview(productReview)
                .product(getProductById(id))
                .build();

    }

    @GetMapping("customer/reviews/name/{name}")
    public List<ProductReviewSummary> getProductReviewsByNameContaining(@PathVariable String name) {
        var products = getProductsByNameContaining(name);


        if(products == null)
            return null;

        return products.stream().map( p-> {
            var review = productReviewRepository.findById(p.id())
                    .orElse(null);
            return ProductReviewSummary.builder().id(p.id())
                    .product(p).productReview(review).build();

        }).toList();

    }
}
