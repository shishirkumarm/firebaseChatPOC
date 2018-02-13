/**
 * Copyright (C) Altimetrik 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.altimetrik.isha.profilemgmt.api.tenant;

/**
 * TenantContext.
 * @author suresh
 *
 */
public class TenantContext {

	  final public static String DEFAULT_TENANT = "tenant_shared_schema";
	
	  /**
	   * ThreadLocal
	   */
	  private static ThreadLocal<String> currentTenant = new ThreadLocal<String>() {
	    @Override
	    protected String initialValue() {
	      return DEFAULT_TENANT;
	    }
	  };
	
	  /**
	   * setCurrentTenant
	   * @param tenant
	   */
	  public static void setCurrentTenant(String tenant) {
	    currentTenant.set(tenant);
	  }
	
	  /**
	   * getCurrentTenant
	   * @return
	   */
	  public static String getCurrentTenant() {
	    return currentTenant.get();
	  }
	
	  /**
	   * clear
	   */
	  public static void clear() {
	    currentTenant.remove();
	  }

}
