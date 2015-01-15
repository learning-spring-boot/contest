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

/**
 * Checks if connection to SendGrid is working and if it's possible to get profile with provided credentials.
 *
 * @author Maciej Walkowiak
 */
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
		try {
			SendGridResponse sendGridResponse = restTemplate.getForObject(GET_PROFILE_URL, SendGridResponse.class,
					getAuthenticationProperties());

			if (sendGridResponse.error != null) {
				builder.down().withDetail("message", sendGridResponse.error.message);
			} else {
				builder.up();
			}
		} catch (Exception e) {
			builder.down().withException(e);
		}

	}

	private Map<String, String> getAuthenticationProperties() {
		Map<String, String> map = new HashMap<>();
		map.put("api_user", sendGridProperties.getUsername());
		map.put("api_password", sendGridProperties.getPassword());

		return map;
	}

	private static class SendGridResponse {
		private final ErrorDetails error;

		@JsonCreator
		public SendGridResponse(@JsonProperty("error") ErrorDetails error) {
			this.error = error;
		}

		private static class ErrorDetails {
			private final String code;
			private final String message;

			@JsonCreator
			public ErrorDetails(@JsonProperty("code") String code, @JsonProperty("message") String message) {
				this.code = code;
				this.message = message;
			}
		}
	}

}
