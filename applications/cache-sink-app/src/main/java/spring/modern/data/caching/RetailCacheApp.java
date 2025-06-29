/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.caching;

import nyla.solutions.core.util.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetailCacheApp {

	public static void main(String[] args) {
		Config.loadArgs(args);
		SpringApplication.run(RetailCacheApp.class, args);
	}

}
