package com.altimetrik.isha.profilemgmt.api.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7720577245515791023L;
	
	private HttpStatus status;
	private String errorMessage;
	
	public ApplicationException (String msg) {
		super(msg);
	}
	
	public ApplicationException(HttpStatus status, String msg) {
		super(msg);
		this.errorMessage = msg;
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}


	public String getErrorMessage() {
		return errorMessage;
	}

	
	
}
