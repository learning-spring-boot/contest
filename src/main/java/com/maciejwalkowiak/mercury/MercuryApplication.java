package com.maciejwalkowiak.mercury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(Configuration.class), useDefaultFilters = false)
@EnableAutoConfiguration
public class MercuryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercuryApplication.class, args);
	}
}
