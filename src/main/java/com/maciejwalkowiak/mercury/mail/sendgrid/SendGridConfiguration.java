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

@Configuration
@ConditionalOnProperty(name = "sendgrid.username")
@ComponentScan
@EnableConfigurationProperties(SendGridProperties.class)
class SendGridConfiguration {
	@Autowired
	private SendGridProperties properties;

	@Bean
	public SendGrid sendGrid() {
		SendGrid sendGrid = new SendGrid(properties.getUsername(), properties.getPassword());

		if (properties.isProxyConfigured()) {
			HttpHost proxy = new HttpHost(properties.getProxy().getHost(), properties.getProxy().getPort());
			CloseableHttpClient http = HttpClientBuilder.create()
					.setProxy(proxy)
					.setUserAgent("sendgrid/" + sendGrid.getVersion() + ";java")
					.build();

			sendGrid.setClient(http);
		}

		return sendGrid;
	}
}
