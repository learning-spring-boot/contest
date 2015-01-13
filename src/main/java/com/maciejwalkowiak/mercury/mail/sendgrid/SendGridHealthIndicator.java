package com.maciejwalkowiak.mercury.mail.sendgrid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
class SendGridHealthIndicator extends AbstractHealthIndicator {
	private static final String GET_PROFILE_URL = "https://api.sendgrid.com/api/profile.get.json";

	private final RestTemplate restTemplate;
	private final SendGridProperties sendGridProperties;

	@Autowired
	SendGridHealthIndicator(@Qualifier("sendGridRestTemplate") RestTemplate restTemplate, SendGridProperties sendGridProperties) {
		this.restTemplate = restTemplate;
		this.sendGridProperties = sendGridProperties;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) throws Exception {
		SendGridResponse sendGridResponse = restTemplate.getForObject(GET_PROFILE_URL, SendGridResponse.class,
				getAuthenticationProperties());

		if (sendGridResponse.error != null) {
			builder.down().withDetail("message", sendGridResponse.error.message);
		} else {
			builder.up();
		}
	}

	private Map<String, String> getAuthenticationProperties() {
		Map<String, String> map = new HashMap<>();
		map.put("api_user", sendGridProperties.getUsername());
		map.put("api_password", sendGridProperties.getPassword());

		return map;
	}

	private static class SendGridResponse {
		private ErrorDetails error;

		@JsonCreator
		public SendGridResponse(@JsonProperty("error") ErrorDetails error) {
			this.error = error;
		}

		private static class ErrorDetails {
			private String code;
			private String message;

			@JsonCreator
			public ErrorDetails(@JsonProperty("code") String code, @JsonProperty("message") String message) {
				this.code = code;
				this.message = message;
			}
		}
	}

}
