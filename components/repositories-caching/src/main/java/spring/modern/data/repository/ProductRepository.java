/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.repository;

import spring.modern.data.domains.customer.Product;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductRepository
 *
 * @author Gregory Green
 */
    @Repository
    public interface ProductRepository
            extends KeyValueRepository<Product,String>
    {
        List<Product> findByNameContaining(String name);
    }

