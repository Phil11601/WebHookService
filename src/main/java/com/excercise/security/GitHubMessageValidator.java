package com.excercise.security;

import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class GitHubMessageValidator implements Authenticator{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int SIGNATURE_LENGTH = 45;
	
	private String signature;
	private String payload;
	private String secret = "secret%key1357";
	private boolean bypassVerification = false;
	
	public GitHubMessageValidator(String signature, String payload){
		this.signature = signature;
		this.payload = payload;
	}
	
	@Override
	public boolean authorized() {
		
		// For now go ahead and authorize if the GitHub message hash matches 
		return verifyMessageSignature();
	}
	
	private boolean verifyMessageSignature() {
		// This can be set in application.properties to test without checking message hash
		if (bypassVerification) {
			return true;
		}
		
		if (signature == null) {
			logger.warn("Message Signature is null");
			return false;
		}
		System.out.println("=====>"+signature+"<======");
		if (signature.length() != SIGNATURE_LENGTH) {
			logger.warn("Message Signature length is invalid");
			return false;
		}
		
		// Check that the hash computed by GitHub matches our computed hash
		String computedHash = String.format("sha1=%s", HmacUtils.hmacSha1Hex(secret, payload));
		System.out.println(computedHash);
        if (!signature.equals(computedHash)) {        	
            return false;
        }
        return true;	
	}
	
}
