package com.altimetrik.isha.profilemgmt.api.exception;

import static com.altimetrik.isha.profilemgmt.api.exception.ErrorConstants.FAILURE;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.altimetrik.isha.profilemgmt.api.response.ProfileResponse;

@EnableWebMvc
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    
    private Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);
	
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
        ProfileResponse errorResponse = new ProfileResponse();
        errorResponse.setStatus(FAILURE);
        errorResponse.setMessage(ex.getMessage());
        HttpStatus status;
        if(ex instanceof ApplicationException) {
        	status = ((ApplicationException) ex).getStatus();
        }else {
        	logger.error("Error occured in App. Error Message is  : "  + ex.getMessage() , ex);
        	status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Object>(errorResponse,status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ProfileResponse errorResponse = new ProfileResponse();
    	errorResponse.setStatus(FAILURE);
    	errorResponse.setMessage("The URL you have reached is not in service (404).");
        return new ResponseEntity<Object>(errorResponse,HttpStatus.NOT_FOUND);
    }
    
}
