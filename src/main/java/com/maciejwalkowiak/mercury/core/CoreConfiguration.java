package com.maciejwalkowiak.mercury.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
@EnableAsync
class CoreConfiguration {
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
