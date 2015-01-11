package de.votesapp.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "yowsupRest")
public class YowsupRestConfig {
	private String hostname;
	private String baseUrl;
	private String username;
	private String password;
}
