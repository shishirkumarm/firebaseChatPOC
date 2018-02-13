package com.altimetrik.isha.profilemgmt.api.tenant;

import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.altimetrik.isha.profilemgmt.api.exception.ApplicationException;
import com.altimetrik.isha.profilemgmt.api.exception.ErrorConstants;
import com.altimetrik.isha.profilemgmt.api.model.repository.CountryDetailsRepository;
import com.altimetrik.isha.profilemgmt.api.tenant.hibernate.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

	private static final String USER_HEADER = "X-User";
	
	private static final String COUNTRY_HEADER = "X-Country";
	
	@Value("${spring.firebase.url}")
	private String fireBaseUrl;

	
	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl connectionProvider;
	
	@Autowired
	private CountryDetailsRepository countryDetailsRepo;
 
	 /**
	  * preHandle
	  */
	 @Override
	 public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
	      throws Exception {
		 
			String countryIdentifier = req.getHeader(COUNTRY_HEADER);
			if(!StringUtils.isEmpty(countryIdentifier)) {
				req.setAttribute(COUNTRY_HEADER, countryIdentifier);
				return true;
			}
			
			String userIdentifier = req.getHeader(USER_HEADER);
			
			
			 RestTemplate restTemplate = new RestTemplate();
			 String loginUri = fireBaseUrl + userIdentifier +"\"";
			 ResponseEntity<String> response = restTemplate.getForEntity(loginUri, String.class);
			 
			 JsonObject jsonObj = (JsonObject) new JsonParser().parse(response.getBody());
			 
			 for (Entry<String, JsonElement> entry : jsonObj.entrySet()) {
				 JsonObject propertyObj = (JsonObject) entry.getValue();
				 if(propertyObj != null) {
					 String uid =  propertyObj.get("uid").getAsString();
					 String location = propertyObj.get("location").getAsString();
					 if(userIdentifier.equals(uid)) {
						 req.setAttribute(COUNTRY_HEADER, location);
					 }
					 if(location == null) {
						 throw new ApplicationException(HttpStatus.UNAUTHORIZED,ErrorConstants.COUNTRY_NOT_FOUND);
					 }
				 }
			}
			return true;
	}
	 
	 	@PostConstruct
	    public void postConstruct() {
	        System.out.print("SampleApplication started: " + fireBaseUrl);
	    }
	
}
