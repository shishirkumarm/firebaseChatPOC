/**
 * Copyright (C) Altimetrik 2017. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */
package com.altimetrik.isha.profilemgmt.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * User Country Tenant model
 * @author sghosh
 *
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="country_details")
public class CountryDetails implements Serializable{

	private static final long serialVersionUID = 6817269084573804692L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailsId;
    
    private String countryIdentifier;
    
    
    @OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
    private CountryIdentity countryIdentity;
    
    
    
    
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryIdentity == null) ? 0 : countryIdentity.hashCode());
		result = prime * result + ((detailsId == null) ? 0 : detailsId.hashCode());
		result = prime * result + ((countryIdentifier == null) ? 0 : countryIdentifier.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryDetails other = (CountryDetails) obj;
		if (countryIdentity == null) {
			if (other.countryIdentity != null)
				return false;
		} else if (!countryIdentity.equals(other.countryIdentity))
			return false;
		if (detailsId == null) {
			if (other.detailsId != null)
				return false;
		} else if (!detailsId.equals(other.detailsId))
			return false;
		if (countryIdentifier == null) {
			if (other.countryIdentifier != null)
				return false;
		} else if (!countryIdentifier.equals(other.countryIdentifier))
			return false;
		return true;
	}
    
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CountryDetails [detailsId=" + detailsId + ", userName=" + countryIdentifier + ", countryIdentity=" + countryIdentity + "]";
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return countryIdentifier;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.countryIdentifier = userName;
	}
	public String getCountryIdentifier() {
		return countryIdentifier;
	}
	public void setCountryIdentifier(String countryIdentifier) {
		this.countryIdentifier = countryIdentifier;
	}
	public CountryIdentity getCountryIdentity() {
		return countryIdentity;
	}
	public void setCountryIdentity(CountryIdentity countryIdentity) {
		this.countryIdentity = countryIdentity;
	}
}
