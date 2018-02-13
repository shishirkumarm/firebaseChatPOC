/**
 * Copyright (C) Altimetrik 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.altimetrik.isha.profilemgmt.api.tenant.hibernate;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import com.altimetrik.isha.profilemgmt.api.tenant.TenantContext;



/**
 * CurrentTenantIdentifierResolverImpl.
 * @author suresh
 *
 */
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	/**
	 * resolveCurrentTenantIdentifier
	 */
	  @Override
	  public String resolveCurrentTenantIdentifier() {
	    return TenantContext.getCurrentTenant();
	  }
	
	  /**
	   * validateExistingCurrentSessions.
	   */
	  @Override
	  public boolean validateExistingCurrentSessions() {
	    return true;
	  }
}