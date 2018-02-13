package com.altimetrik.isha.profilemgmt.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimetrik.isha.profilemgmt.api.model.CountryDetails;


public interface CountryDetailsRepository extends JpaRepository<CountryDetails,Long>{
	CountryDetails findCountryNameByCountryIdentifier(String countryIdentifier);

}
