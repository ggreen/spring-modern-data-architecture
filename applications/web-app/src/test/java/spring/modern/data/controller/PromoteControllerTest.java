/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.controller;

import spring.modern.data.domains.customer.Product;
import spring.modern.data.domains.customer.Promotion;
import spring.modern.data.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static nyla.solutions.core.util.Organizer.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromoteControllerTest
{
    @Mock
    private PromotionRepository repository;
    private Promotion expected = new Promotion("1",
            "new stuff",
            toList(new Product("3L","productName")));

    @Test
    void given_promote_When_savePromotion_Then_getPromote_returns_Saved()
    {
        when(repository.findById(anyString())).thenReturn(Optional.of(expected));
        System.out.println(expected);
        var subject = new PromoteController(repository);

        subject.savePromotion(expected);
        assertEquals(expected,subject.getPromotion(expected.id()));
        verify(repository).findById(anyString());
    }
}