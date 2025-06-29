/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.repository;

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

