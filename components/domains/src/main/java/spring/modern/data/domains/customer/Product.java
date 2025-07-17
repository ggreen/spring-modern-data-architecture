/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.domains.customer;

import lombok.Builder;

@Builder
public record Product(String id, String name)
{
}
