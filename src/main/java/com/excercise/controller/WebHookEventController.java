package com.excercise.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excercise.model.GitHubNotification;
import com.excercise.model.Message;
import com.excercise.model.Notification;
import com.excercise.model.SlackMessage;
import com.excercise.security.Authenticator;
import com.excercise.security.GitHubMessageValidator;
import com.excercise.service.SlackWebHookService;

@RestController
class GitHubEventController{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SlackWebHookService slackWebHookService;
   
	@RequestMapping(value = "/payload", method = RequestMethod.POST)
	public ResponseEntity<String> handle(@RequestHeader("X-Hub-Signature") String signature,
		@RequestHeader("X-GitHub-Event") String gitHubEvent,
		@RequestBody String payload){
		
		HttpHeaders headers = new HttpHeaders();
        Message slackMessage;
        
        Authenticator authenticator = new GitHubMessageValidator(signature, payload);
        if(!authenticator.authorized()){
    		logger.warn("Unauthorized post received");
        	return new ResponseEntity<>("Unauthorized post.\n", headers,
                    HttpStatus.UNAUTHORIZED);
        }
        
        try {
        	
        	Notification gitHubNotification = new GitHubNotification(payload);
        	slackMessage = new SlackMessage(gitHubNotification);
        	slackWebHookService.sendRestPost(slackMessage.getMessage());
      	
        } catch (Exception e) {
        	logger.warn("Unable to process GitHub Event: " + payload);
        	return new ResponseEntity<>("Error", headers, HttpStatus.BAD_REQUEST);		
        }      
        return new ResponseEntity<>("Success", headers, HttpStatus.OK);			
	}
}
	