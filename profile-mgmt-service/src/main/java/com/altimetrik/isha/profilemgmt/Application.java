/**
 * Copyright (C) Altimetrik 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.altimetrik.isha.profilemgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * SpringBoot Application.
 * @author suresh
 *
 */
@SpringBootApplication
@PropertySource("classpath:/application_local.properties")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}
}
