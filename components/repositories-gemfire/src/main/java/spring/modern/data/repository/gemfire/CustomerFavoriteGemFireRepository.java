/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.repository.gemfire;

import spring.modern.data.domains.customer.CustomerFavorites;
import spring.modern.data.repository.CustomerFavoriteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("gemfire")
public interface CustomerFavoriteGemFireRepository extends CustomerFavoriteRepository, GemfireRepository<CustomerFavorites,String> {
}
