/**
 * Copyright (C) Altimetrik 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.altimetrik.isha.profilemgmt.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * NotFoundException.
 * @author suresh
 *
 */

@SuppressWarnings("unused")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2430417759080092391L;
	private int code;
	
	public NotFoundException (int code, String msg) {
		super(msg);
		this.code = code;
	}
}
