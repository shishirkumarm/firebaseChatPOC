/**
 * Copyright (C) Altimetrik 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.altimetrik.isha.profilemgmt.api.tenant;

import static com.altimetrik.isha.profilemgmt.api.config.DatabaseConfigListener.COUNTRY_LIST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.altimetrik.isha.profilemgmt.api.exception.ApplicationException;
import com.altimetrik.isha.profilemgmt.api.exception.ErrorConstants;
import com.altimetrik.isha.profilemgmt.api.model.repository.CountryDetailsRepository;
import com.altimetrik.isha.profilemgmt.api.tenant.hibernate.DataSourceBasedMultiTenantConnectionProviderImpl;

/**
 * TenantInterceptor.
 * @author suresh
 *
 */
@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

	private static final String COUNTRY_HEADER = "X-Country";
	
	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
	
	@Autowired
	private CountryDetailsRepository countryDetailsRepo;
 
	 /**
	  * preHandle
	  */
	 @Override
	 public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
	      throws Exception {
		 
			String tenantIdentifier = (String) req.getAttribute(COUNTRY_HEADER);
			
			if(!COUNTRY_LIST.contains(tenantIdentifier)) {
				throw new ApplicationException(HttpStatus.UNAUTHORIZED,ErrorConstants.COUNTRY_NOT_FOUND); 
			}
			
			TenantContext.setCurrentTenant(tenantIdentifier);
			
			return true;
		}
	
	 /**
	  * postHandle
	  */
	  @Override
	  public void postHandle(
			  HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
	          throws Exception {
	    TenantContext.clear();
	  }
}
