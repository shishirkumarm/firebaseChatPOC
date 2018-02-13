package com.altimetrik.isha.profilemgmt.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.isha.profilemgmt.api.model.ProfileEncryptionConfig;

public interface ProfileTemplateConfigRepository extends JpaRepository<ProfileEncryptionConfig,String>{

}
