package com.altimetrik.isha.profilemgmt.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;

import com.altimetrik.isha.profilemgmt.api.exception.ApplicationException;
import com.altimetrik.isha.profilemgmt.api.exception.ErrorConstants;

public enum EncryptionEnum {
	
	 AES("AES") {
		@Override
		public String encrypt(String input) throws ApplicationException {
			try{
				if(input == null) {
					return input;
				}
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
		        return Base64.encodeBase64String(cipher.doFinal(input.getBytes("UTF-8")));
			}catch(Exception e) {
				throw  new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.ENCRYPTION_ERROR);
			}
		}
		
		private SecretKeySpec getSecretKey() throws UnsupportedEncodingException, NoSuchAlgorithmException{
			String myKey = "ALTI_ISHA";
			byte[] key;
			SecretKeySpec secretKey ;
			MessageDigest sha = null;
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
		    key = Arrays.copyOf(key, 16); // use only first 128 bit
			secretKey = new SecretKeySpec(key, "AES");
			return secretKey;
		}

		@Override
		public String decrypt(String input) throws ApplicationException{
			try
	        {
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
	            return new String(cipher.doFinal(Base64.decodeBase64(input)));
	        }
	        catch (Exception e)
	        {
	        	throw  new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.ENCRYPTION_ERROR);
	        }
		}
	},

	;

	
	
	
	
	
	
	
	
	private String encryptionType;


	private EncryptionEnum(String encryptionType) {
		this.encryptionType = encryptionType;
	}
	
	public abstract String encrypt(String value)throws ApplicationException;
	
	public abstract String decrypt(String value) throws ApplicationException;
	
	public String getEncryptionType() {
		return encryptionType;
	}
	
	
	
	
	
	

	private static Map<String, EncryptionEnum> ENUMMAP;
	static{
		ENUMMAP = new HashMap<>();
		for(EncryptionEnum statusEnum : values()){
			ENUMMAP.put(statusEnum.getEncryptionType(), statusEnum );
		}
	}
	
	public static EncryptionEnum getEncryption(String encryptionType) {
		return ENUMMAP.get(encryptionType);
	}
	
}
