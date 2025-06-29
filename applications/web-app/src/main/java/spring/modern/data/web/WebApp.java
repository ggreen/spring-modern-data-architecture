/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package spring.modern.data.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApp
{
	public static void main(String[] args) {
		SpringApplication.run(WebApp.class, args);
	}
}
