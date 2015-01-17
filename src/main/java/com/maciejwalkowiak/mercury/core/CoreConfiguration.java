package com.maciejwalkowiak.mercury.core;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
@EnableAsync
class CoreConfiguration {
	@Configuration
	@ConditionalOnProperty(name = "mercury.db.inMemory", havingValue = "true")
	static class FongoConfig {
		@Bean
		Mongo mongo() {
			return new Fongo("mongo").getMongo();
		}
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
