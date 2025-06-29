/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.analytics.consumers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


        @Entity
        @Table(name = "customer_orders", schema = "retail")
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public class CustomerOrderEntity
        {
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                private long id;

                private Long orderId;

                private String customerId;

                @Embedded
                private ProductOrderEntity productOrder;
        }
