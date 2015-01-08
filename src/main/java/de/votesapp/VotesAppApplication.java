package de.votesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@EnableReactor
public class VotesAppApplication {
	public static void main(final String[] args) {
		SpringApplication.run(VotesAppApplication.class, args);
	}

	@Bean
	public Reactor rootReactor(final Environment env) {
		return Reactors.reactor().env(env).get();
	}
}
