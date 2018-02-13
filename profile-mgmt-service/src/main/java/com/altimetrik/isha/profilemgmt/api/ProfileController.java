package com.altimetrik.isha.profilemgmt.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.altimetrik.isha.profilemgmt.api.exception.ApplicationException;
import com.altimetrik.isha.profilemgmt.api.exception.ErrorConstants;
import com.altimetrik.isha.profilemgmt.api.exception.NotFoundException;
import com.altimetrik.isha.profilemgmt.api.model.ProfileDTO;
import com.altimetrik.isha.profilemgmt.api.response.ProfileResponse;
import com.altimetrik.isha.profilemgmt.api.service.ProfileService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author mmahalingam
 *
 */
@RestController
@Api(value="profile",tags = "Profile Management")
@RequestMapping(value = "/profile" )
public class ProfileController {
	
	
		private static final String USER_HEADER = "X-User";
	
	    @Autowired
	    private ProfileService service;
	    
	    private Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	    /**
	     * setService.
	     * @param service
	     */
		public void setService(ProfileService service) {
			this.service = service;
		}
	
		/**
		 * 
		 * @param profileRequest
		 * @param file
		 * @return
		 * @throws NotFoundException
		 * @throws IOException
		 */
		@ApiOperation(notes = "X-User in request is mandatory", value = "To create a new Profile", code = 201 ) 
		@ApiImplicitParams({@ApiImplicitParam(name = "X-User", value = "User Identifier",dataType = "string", paramType = "header")})
		@ApiResponses(value = { 
		        @ApiResponse(code = 201, message = "successful operation", response = ProfileResponse.class),
		        @ApiResponse(code = 503, message = "Service Temporary Unavailable", response = ProfileResponse.class),
		        @ApiResponse(code = 500, message = "Internal server Error", response = ProfileResponse.class),
		        @ApiResponse(code = 401, message = "Unautorized user", response = ProfileResponse.class)})
		@RequestMapping( produces = MediaType.APPLICATION_JSON_VALUE, consumes = { "multipart/mixed", "multipart/form-data" }, method = RequestMethod.POST)
		@ResponseStatus(HttpStatus.CREATED)
		public ResponseEntity<ProfileResponse> createProfile(@RequestPart(name="profileRequest") String profileRequest,  @RequestPart(name="profileimg",required = false)  MultipartFile file, HttpServletRequest req) throws ApplicationException {
			
			 logger.info("Profile controller executing for  createProfile ");
			 ProfileDTO profileDTO = convertProfileDTO(profileRequest,file,req);
			 

			 service.createProfile(profileDTO);
			 
			 ProfileResponse response = new ProfileResponse();
			 response.setStatus(ErrorConstants.SUCCESS);
			 response.setMessage("Saved Successfuly");
			 
			 return new ResponseEntity<ProfileResponse>(response, HttpStatus.CREATED);
			 
		}
		
		
		/**
		 * 
		 * @param profileRequest
		 * @param file
		 * @param profileId
		 * @return
		 * @throws NotFoundException
		 * @throws JsonParseException
		 * @throws JsonMappingException
		 * @throws IOException
		 * @throws ApplicationException 
		 */
		@ApiOperation(notes = "X-User in request is mandatory", value = "To update a new Profile", code = 200) 
		@ApiImplicitParams({@ApiImplicitParam(name = "X-User", value = "User Identifier",dataType = "string", paramType = "header")})
		@ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation", response = ProfileResponse.class),@ApiResponse(code = 503, message = "Service Temporary Unavailable", response = ProfileResponse.class),@ApiResponse(code = 500, message = "Internal server Error", response = ProfileResponse.class),@ApiResponse(code = 401, message = "Unautorized user", response = ProfileResponse.class)})
		
		@RequestMapping( produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
		public ResponseEntity<ProfileResponse> updateProfile(@ApiParam(name="profileRquest", value = "Request Json body for updating profile for the user.", required=true)
		@RequestPart(name="profileRequest") String profileRequest,   @ApiParam(name="profileimg") @RequestPart(name="profileimg",required = false)  MultipartFile file,@ApiParam(name="profileId", value = "Profile ID whose details to be fetched. ProfileId and userID are the same", required=true)@PathVariable("profileId") String profileId, HttpServletRequest req) throws  ApplicationException {
			 
			ProfileDTO profileDTO = convertProfileDTO(profileRequest,file,req);
			
			service.updateProfile(profileDTO);
			ProfileResponse response = new ProfileResponse();
			response.setStatus(ErrorConstants.SUCCESS);
			response.setMessage("Updated Successfuly"); 
			
			return new ResponseEntity<ProfileResponse>(response, HttpStatus.OK);
			 
		}
	 
	 
	 
	 	/**
		 * findByTriggerId.
		 * @param alertId
		 * @return
		 * @throws NotFoundException
	 	 * @throws ApplicationException 
		 */
		@ApiOperation(notes = "X-User in request is mandatory", value = "To get all profile details of a particular user  with given ProfileId")
		@ApiImplicitParams({@ApiImplicitParam(name = "X-User", value = "User Identifier",dataType = "string", paramType = "header")})
		@ApiResponses(value = {@ApiResponse(code = 201, message = "Successful operation", response = ProfileDTO.class),@ApiResponse(code = 503, message = "Service Temporary Unavailable", response = ProfileResponse.class),@ApiResponse(code = 500, message = "Internal server Error", response = ProfileResponse.class),@ApiResponse(code = 401, message = "Unautorized user", response = ProfileResponse.class)})
	    @RequestMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
		
		public ResponseEntity<ProfileDTO> findByProfileId(HttpServletRequest req)
				throws  ApplicationException {
			String profileId = req.getHeader(USER_HEADER);
			ProfileDTO profileDTO  =  service.getProfile(profileId);	
			return new ResponseEntity<ProfileDTO>(profileDTO, HttpStatus.OK);
			
		}
	 
		
		private ProfileDTO convertProfileDTO(String profileRequest, MultipartFile file,HttpServletRequest req ) throws ApplicationException {
			ProfileDTO profileDTO = null; 
			try {
				 profileDTO =  new ObjectMapper().readValue(profileRequest.replace("'", "\""), ProfileDTO.class);
				 profileDTO.setProfilePhoto(file != null ? file.getBytes() : null);
			 }catch(Exception e) {
				 throw new ApplicationException(HttpStatus.BAD_REQUEST,"UnRecoginized Input");
			 }
			 String headerProfileId = req.getHeader(USER_HEADER);
			 if(!profileDTO.getProfileId().equals(headerProfileId)) {
				 throw new ApplicationException(HttpStatus.NOT_ACCEPTABLE, ErrorConstants.IDCONFLICT);
				 
			 }
			 return profileDTO;
		}
	

}
