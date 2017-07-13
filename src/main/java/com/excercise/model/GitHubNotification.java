package com.excercise.model;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

public class GitHubNotification implements Notification {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	private Map<String, Object> gitEvent;
	
	public GitHubNotification(String payload) {
		try {
		JsonParser springParser = JsonParserFactory.getJsonParser();
    	gitEvent = springParser.parseMap(payload);
        } catch (Exception e) {
        	logger.warn("Unparsable JSON received for GitHub event: " + payload);
        }
	}
	
	@Override
	public Map<String, Object> getNotification(){
		return this.gitEvent;
	}
}
