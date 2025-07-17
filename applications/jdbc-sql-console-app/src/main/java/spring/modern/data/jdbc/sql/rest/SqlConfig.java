/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.jdbc.sql.rest;

import nyla.solutions.core.patterns.jdbc.Sql;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlConfig {
    @Bean
    Sql sql()
    {
        return new Sql();
    }
}
