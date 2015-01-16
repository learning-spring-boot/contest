package com.maciejwalkowiak.mercury.mail.sendgrid;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "sendgrid.username")
@ComponentScan
@EnableConfigurationProperties(SendGridProperties.class)
class SendGridConfiguration {
	@Autowired
	private SendGridProperties properties;

	@Bean
	public SendGrid sendGrid() {
		return new SendGrid(properties.getUsername(), properties.getPassword());
	}
}
