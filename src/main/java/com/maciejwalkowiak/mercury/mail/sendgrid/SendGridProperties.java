package com.maciejwalkowiak.mercury.mail.sendgrid;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sendgrid")
class SendGridProperties {
	/**
	 * SendGrid username
	 */
	private String username;

	/**
	 * SendGrid password
	 */
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
