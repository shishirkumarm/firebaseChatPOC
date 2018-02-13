package com.altimetrik.isha.profilemgmt.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "This model provides Response for  create/update profile ")
public class ProfileResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1771204448316504151L;

	@ApiModelProperty(notes = "Status of the action.")
	private String status;
	
	@ApiModelProperty(notes = "Message for the particular action")
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
