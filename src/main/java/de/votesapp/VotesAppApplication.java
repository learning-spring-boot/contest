package de.votesapp;

import org.apache.http.HttpHost;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;

import de.votesapp.client.AuthHttpComponentsClientHttpRequestFactory;
import de.votesapp.client.YowsupRestConfig;

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

	@Profile("yowsup")
	@Bean
	public RestTemplate restTemplate(final YowsupRestConfig config) {
		final AuthHttpComponentsClientHttpRequestFactory requestFactory = new AuthHttpComponentsClientHttpRequestFactory( //
				new HttpHost(config.getHostname()), //
				config.getUsername(), //
				config.getPassword());

		return new RestTemplate(requestFactory);
	}

	// TODO: That should be use in the test (profile) only. Since we have not
	// real database config, we let it here.
	@Configuration
	static class MongoDbTestConfiguration extends AbstractMongoConfiguration {

		@Override
		public MappingMongoConverter mappingMongoConverter() throws Exception {
			final MappingMongoConverter mappingMongoConverter = super.mappingMongoConverter();
			// WhatsApp Keys are containing dots in domains.
			mappingMongoConverter.setMapKeyDotReplacement("_");
			return mappingMongoConverter;
		}

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
