package com.maciejwalkowiak.mercury.mail.sendgrid;

import com.sendgrid.SendGrid;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

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
