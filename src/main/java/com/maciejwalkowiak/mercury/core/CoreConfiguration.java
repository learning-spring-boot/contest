package com.maciejwalkowiak.mercury.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;

@Configuration
@ComponentScan
class CoreConfiguration {

	@Bean
	public Reactor rootReactor(Environment env) {
		return Reactors.reactor().env(env).get();
	}
}
