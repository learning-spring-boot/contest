package de.votesapp.commands.plugins;

import java.util.Optional;

import lombok.Data;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;

@Service
public class ChuckNorrisCommandPlugin extends TextEqualsWordPlugin {

	public static final String[] DEFAULT_CHUCKS = { "chuck", "chucknorris", "chuck norris", "icndb" };

	public ChuckNorrisCommandPlugin() {
		super(DEFAULT_CHUCKS);
	}

	@Override
	public Optional<Answer> matches(final GroupMessage message, final Group group) {
		return Optional.of(Answer.intoGroup(group, fetchNewJoke()));
	}

	public String fetchNewJoke() {
		final RestTemplate restTemplate = new RestTemplate();
		final ChuckNorrisJoke root = restTemplate.getForObject("http://api.icndb.com/jokes/random/", ChuckNorrisJoke.class);
		return root.getValue().getJoke();
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ChuckNorrisJoke {
		Value value;
	}

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Value {
		String joke;
	}
}
