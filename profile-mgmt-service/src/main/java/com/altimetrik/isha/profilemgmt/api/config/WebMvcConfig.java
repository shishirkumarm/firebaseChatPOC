/**
 * Copyright (C) Altimetrik 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.altimetrik.isha.profilemgmt.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.altimetrik.isha.profilemgmt.api.tenant.TenantInterceptor;
import com.altimetrik.isha.profilemgmt.api.tenant.UserInterceptor;

/**
 * WebMvcConfig
 * @author suresh
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @Autowired
  TenantInterceptor tenantInterceptor;
  
  @Autowired
  UserInterceptor userInterceptor;
  
  @Bean  // Magic entry 
  public DispatcherServlet dispatcherServlet() {
      DispatcherServlet ds = new DispatcherServlet();
      ds.setThrowExceptionIfNoHandlerFound(true);
      return ds;
  }
  /**
   * addInterceptors.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(userInterceptor); 
    registry.addInterceptor(tenantInterceptor);
  }
}
