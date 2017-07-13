package com.excercise.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class SlackWebHookService implements WebHookService 
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private Environment env;
	
	@Override
	public void sendRestPost(String message) {
	
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        Map map = new HashMap<String, String>();
        map.put("Content-Type", "application/json");

        headers.setAll(map);

        Map req_payload = new HashMap();
        req_payload.put("text", message);

        HttpEntity<?> request = new HttpEntity<>(req_payload, headers);
        String WebHookUrl = env.getProperty("slack.webhook.url");

        ResponseEntity<?> response = new RestTemplate().postForEntity(WebHookUrl, request, String.class);
        HttpStatus statusCode = response.getStatusCode();
        if (statusCode == HttpStatus.ACCEPTED) {
        	String result = (String) response.getBody();
        } else {
        	logger.error(String.format("Error posting event to Slack: ", statusCode.toString()));
        }

	}

}
