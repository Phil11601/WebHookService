package com.excercise.model;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SlackMessage implements Message {
	
	private Notification gitNotification;
	
	public SlackMessage(Notification notification) {
		this.gitNotification = notification;
	}
	
	@Override 
	public String getMessage() {
		return constructMessage();
	}
	
	private String constructMessage() {
		StringBuilder message = new StringBuilder();
				
    	gitNotification.getNotification().forEach((k,v) -> {
    		System.out.println(k + getAbbreviatedValue(v.toString()));
    		message.append(k +  getAbbreviatedValue(v.toString()));        
    	});	
    	
    	return message.toString();
	}
	
	private String getAbbreviatedValue(String fullValue){
		int maxStringLength = 53;
		int firstPartLength = 25;
		int lastPartLength = 25;
		
		if (fullValue.length() <= 53) {
			return fullValue;
		}
		
		StringBuilder shortenedValue = new StringBuilder();
		shortenedValue.append(fullValue.substring(0,firstPartLength));
		shortenedValue.append("...");
		shortenedValue.append(fullValue.substring(fullValue.length() - lastPartLength));
		shortenedValue.append("\n");
		
		return shortenedValue.toString();
	}

}
