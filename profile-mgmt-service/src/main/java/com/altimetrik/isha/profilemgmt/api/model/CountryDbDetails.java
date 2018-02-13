package com.altimetrik.isha.profilemgmt.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="country_db_details")
public class CountryDbDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6771031401439366747L;

	@Id
	@Column(name = "country_name")
	private String countryName;
	
	
	@Column(name = "db_user_name")
	private String dbUserName;
	
	@Column(name = "db_password")
	private String dbPassword;
	
	@Column(name = "db_url")
	private String dbUrl;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getDbUserName() {
		return dbUserName;
	}

	public void setDbUserName(String dbUserName) {
		this.dbUserName = dbUserName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	
	
	
	
}
