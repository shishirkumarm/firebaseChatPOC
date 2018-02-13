package com.altimetrik.isha.profilemgmt.api.service.impl;

import static com.altimetrik.isha.profilemgmt.api.exception.ErrorConstants.PROFILE_ALREADY_EXIST;
import static com.altimetrik.isha.profilemgmt.api.exception.ErrorConstants.NO_CONTENT;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.isha.profilemgmt.api.ProfileController;
import com.altimetrik.isha.profilemgmt.api.exception.ApplicationException;
import com.altimetrik.isha.profilemgmt.api.model.Profile;
import com.altimetrik.isha.profilemgmt.api.model.ProfileDTO;
import com.altimetrik.isha.profilemgmt.api.model.ProfileEncryptionConfig;
import com.altimetrik.isha.profilemgmt.api.model.repository.ProfileRepository;
import com.altimetrik.isha.profilemgmt.api.model.repository.ProfileTemplateConfigRepository;
import com.altimetrik.isha.profilemgmt.api.service.ProfileService;
import com.altimetrik.isha.profilemgmt.api.util.EncryptionEnum;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

	private static final String SET="set", GET = "get";
	
	@Autowired
	ProfileTemplateConfigRepository templateRepository;
	
	@Autowired
	ProfileRepository profileRepository;
	
    private Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	
	@Override
	public boolean createProfile(ProfileDTO profileDto) throws ApplicationException {
		Profile profileEntity = profileRepository.findOne(profileDto.getProfileId());
		if(profileEntity != null) throw new ApplicationException(HttpStatus.CONFLICT,PROFILE_ALREADY_EXIST);
		profileEntity = new Profile();
		setProfileEntity(profileEntity, profileDto);
		profileRepository.save(profileEntity);
		return true;
	}
	
	
	public boolean updateProfile(ProfileDTO profileDto) throws ApplicationException{
		Profile profileEntity = profileRepository.findOne(profileDto.getProfileId());
		if(profileEntity == null) throw new ApplicationException(HttpStatus.NOT_FOUND,NO_CONTENT);
		setProfileEntity(profileEntity, profileDto);
		profileRepository.save(profileEntity);
		return true;
	}
	
	
	
	@Override
	public ProfileDTO getProfile(String uuid) throws ApplicationException {
		Profile profileEntity = profileRepository.findOne(uuid);
		if(profileEntity == null) throw new ApplicationException(HttpStatus.NOT_FOUND,NO_CONTENT);
		List<ProfileEncryptionConfig> templateConfigs = templateRepository.findAll();
		ProfileDTO profileDto  = new ProfileDTO();
		for (ProfileEncryptionConfig propertyConfig : templateConfigs) {
			setEntity(propertyConfig.getEntityPropertyName(), propertyConfig.getRequestPropertyName(), profileEntity, profileDto, propertyConfig, false);
		}
		profileDto.setProfilePhoto(profileEntity.getProfilePhoto());
		return profileDto;
	}
	
	
	
	
	private void setProfileEntity(Profile profileEntity,ProfileDTO profileDto ) {
		List<ProfileEncryptionConfig> templateConfigs = templateRepository.findAll();
		for (ProfileEncryptionConfig propertyConfig : templateConfigs) {
			setEntity(propertyConfig.getRequestPropertyName(), propertyConfig.getEntityPropertyName(), profileDto, profileEntity, propertyConfig,true);
		}	
		profileEntity.setProfilePhoto(profileDto.getProfilePhoto());
	}

	private void setEntity(String srcPropertyName,String destPropertyName, Object srcObject, Object destObject,ProfileEncryptionConfig profileEncryptConfig, boolean isEncrypt) {
		try {
		      Method dtotMethod = srcObject.getClass().getMethod(GET+srcPropertyName);
		      String dtoValue = (String) dtotMethod.invoke(srcObject);
		      
		      if(profileEncryptConfig.isEncryptionAllowed() && profileEncryptConfig.getEncryptionType() != null ) {
		    	  dtoValue = isEncrypt ? EncryptionEnum.getEncryption(profileEncryptConfig.getEncryptionType()).encrypt(dtoValue) : EncryptionEnum.getEncryption(profileEncryptConfig.getEncryptionType()).decrypt(dtoValue) ;  
		      }
		      Method entityMethod = destObject.getClass().getMethod(SET+profileEncryptConfig.getEntityPropertyName(), String.class);
		      entityMethod.invoke(destObject, dtoValue);
		       
		  }catch(Exception e) {
		    	logger.error("Error is occured while setting value Exception : "  + e.getMessage() ,  e);
		  }
	}
	

}
