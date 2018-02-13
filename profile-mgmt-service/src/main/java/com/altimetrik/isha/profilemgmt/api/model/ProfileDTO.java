package com.altimetrik.isha.profilemgmt.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "This model provides profile information for the user ")
@JsonInclude(content=Include.NON_NULL)
public class ProfileDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8963702949325074289L;
	@ApiModelProperty(required = true, notes = "userId of the user", example ="ishaguru@gmail.com")
	@JsonProperty
	private String profileId;
	
	@ApiModelProperty(required = true, notes = "Full Name of the user", example="Guptip Patel")
	@JsonProperty
	private String fullName;
	
	@ApiModelProperty(required = true, notes = "last Name of the user",example="Patel")
	@JsonProperty
	private String lastName;
	
	@ApiModelProperty(required = true, notes = "Email ID of the user", example="ishaguru@gmail.com")
	@JsonProperty
	private String email;
	
	
	@ApiModelProperty(required = true, value="The dob is date of birth for the user and the format should be YYYY-MM-DD.", example= "1980-10-19")
	@JsonProperty
	private String dob;
	
	@ApiModelProperty( notes = "Occupation", example="Software" )
	@JsonProperty
	private String occupation;
	
	@ApiModelProperty( notes = "Company" ,example = "Altimetrik pvt Ltd")
	@JsonProperty
	private String company;
	
	@ApiModelProperty( notes = "designation", example = "Senior Staff Engineer")
	@JsonProperty
	private String designation;
	
	@ApiModelProperty( notes = "Address",example = "Plot 19, Taramani, Chennai -600016")
	@JsonProperty
	private String address;
	
	@ApiModelProperty( notes = "Phone Number for the user",example ="9999999999")
	@JsonProperty
	private String phoneNumber;
	
	@ApiModelProperty( notes = "Account Preference")
	@JsonProperty
	private String accountPreference;
	
	@ApiModelProperty( notes = "Profile photo byte aarray will be used for retrieval process")
	@JsonProperty
	private byte[] profilePhoto;
	
	
	
	
	public byte[] getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAccountPreference() {
		return accountPreference;
	}
	public void setAccountPreference(String accountPreference) {
		this.accountPreference = accountPreference;
	}
	
	
	
}
