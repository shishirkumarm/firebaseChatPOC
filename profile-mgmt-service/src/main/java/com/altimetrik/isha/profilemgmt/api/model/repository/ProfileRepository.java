package com.altimetrik.isha.profilemgmt.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.isha.profilemgmt.api.model.Profile;


public interface ProfileRepository extends JpaRepository<Profile,String>{

}
