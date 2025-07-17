/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.domains.customer;

/**
 Example JSON
 { "customerId" : "g01" }

 * @param customerId
 */
public record CustomerIdentifier(String customerId) {
}
