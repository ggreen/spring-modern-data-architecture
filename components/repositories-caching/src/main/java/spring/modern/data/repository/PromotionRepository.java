/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.repository;

import spring.modern.data.domains.customer.Promotion;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

/**
 * PromotionRepository
 *
 * @author Gregory Green
 */

    public interface PromotionRepository
        extends KeyValueRepository<Promotion,String>
    {
    }

