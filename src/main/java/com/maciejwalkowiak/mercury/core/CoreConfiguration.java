package com.maciejwalkowiak.mercury.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
class CoreConfiguration {
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
