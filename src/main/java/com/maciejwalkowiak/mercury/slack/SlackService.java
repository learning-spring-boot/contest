package com.maciejwalkowiak.mercury.slack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class SlackService {
	private final RestTemplate restTemplate;
	private final String slackWebHookUrl;

	@Autowired
	public SlackService(RestTemplate restTemplate, @Value("${slack.hook.url}") String slackWebHookUrl) {
		this.restTemplate = restTemplate;
		this.slackWebHookUrl = slackWebHookUrl;
	}

	public void send(SlackRequest slackRequest) {
		restTemplate.postForEntity(slackWebHookUrl, slackRequest, String.class);
	}
}
