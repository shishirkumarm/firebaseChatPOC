package com.altimetrik.isha.profilemgmt.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="profile_encryption_config")
public class ProfileEncryptionConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6690588090128147676L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_config_id")
	private Long profilConfigId;
	
	@Column(name = "req_property_id")
	private String requestPropertyName;

	@Column(name = "entity_property_id")
	private String entityPropertyName;
	
	@Column(name = "encryption_type")
	private String encryptionType;
	
	@Column(name = "isencryptionallowed")
	private boolean encryptionAllowed;
	
	
	
	
	public Long getProfilConfigId() {
		return profilConfigId;
	}
	public void setProfilConfigId(Long profilConfigId) {
		this.profilConfigId = profilConfigId;
	}
	public String getRequestPropertyName() {
		return requestPropertyName;
	}
	public void setRequestPropertyName(String requestPropertyName) {
		this.requestPropertyName = requestPropertyName;
	}
	public String getEntityPropertyName() {
		return entityPropertyName;
	}
	public void setEntityPropertyName(String entityPropertyName) {
		this.entityPropertyName = entityPropertyName;
	}
	public boolean isEncryptionAllowed() {
		return encryptionAllowed;
	}
	public void setEncryptionAllowed(boolean encryptionAllowed) {
		this.encryptionAllowed = encryptionAllowed;
	}
	public String getEncryptionType() {
		return encryptionType;
	}
	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}
	
	
	
}
