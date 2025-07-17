/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.controller;

import lombok.RequiredArgsConstructor;
import spring.modern.data.domains.customer.Product;
import spring.modern.data.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import spring.modern.data.service.QueryProductService;

import java.util.List;
import java.util.Locale;

/**
 * ProductController
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
}
