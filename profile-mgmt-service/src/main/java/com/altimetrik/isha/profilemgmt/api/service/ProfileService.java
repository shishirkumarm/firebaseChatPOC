package com.altimetrik.isha.profilemgmt.api.service;

import com.altimetrik.isha.profilemgmt.api.exception.ApplicationException;
import com.altimetrik.isha.profilemgmt.api.model.ProfileDTO;

public interface ProfileService {

	boolean createProfile(ProfileDTO request) throws ApplicationException;
	boolean updateProfile(ProfileDTO request) throws ApplicationException;
	ProfileDTO getProfile(String uuid) throws ApplicationException;
}
