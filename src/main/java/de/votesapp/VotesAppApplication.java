package de.votesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
public class VotesAppApplication {

	public static void main(final String[] args) {
		SpringApplication.run(VotesAppApplication.class, args);
	}
}
