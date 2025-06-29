/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.web.controller;

import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.repository.PromotionRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("promotions")
public record PromoteController(PromotionRepository repository)
{

    private static final String channel = "default";

    @PostMapping("promotion")
    public void savePromotion(@RequestBody Promotion promotion)
    {
        repository.save(promotion);
    }

    @PostMapping("promotion/publish")
    public void publishPromotion(@RequestBody Promotion promotion)
    {
        savePromotion(promotion);
    }


    @GetMapping("promotion/{id}")
    public Promotion getPromotion(@PathVariable String id)
    {
        return repository.findById(id).orElse(null);
    }
}
