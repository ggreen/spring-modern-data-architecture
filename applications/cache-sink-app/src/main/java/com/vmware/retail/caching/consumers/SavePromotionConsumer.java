/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package com.vmware.retail.caching.consumers;

import com.vmware.retail.domain.Promotion;
import com.vmware.retail.repository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 *
 * @param promotionRepository the key/value repository to save
 */
@Component
@Slf4j
public record SavePromotionConsumer(PromotionRepository promotionRepository) implements Consumer<Promotion> {

    @Override
    public void accept(Promotion promotion) {
        log.info("Saving Promotion: {}",promotion);
        promotionRepository.save(promotion);

    }
}
