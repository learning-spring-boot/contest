package de.votesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

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

	// TODO: That should be use in the test (profile) only. Since we have not
	// real database config, we let it here.
	@Configuration
	static class MongoDbTestConfiguration extends AbstractMongoConfiguration {

		@Override
		protected String getDatabaseName() {
			return "test";
		}

		@Override
		public Mongo mongo() {
			return new Fongo("mongo").getMongo();
		}

		@Override
		protected String getMappingBasePackage() {
			return "de.votesapp";
		}
	}
}
