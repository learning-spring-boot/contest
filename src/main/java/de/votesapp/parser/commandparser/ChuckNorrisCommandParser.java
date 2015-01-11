package de.votesapp.parser.commandparser;

import lombok.Data;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import reactor.core.Reactor;
import reactor.event.Event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class ChuckNorrisCommandParser extends TextEqualsWordParser implements Describable {

	public static final String[] DEFAULT_CHUCKS = { "chuck", "chucknorris", "chuck norris", "icndb" };

	public ChuckNorrisCommandParser() {
		super(new ChuckNorrisCommand(), DEFAULT_CHUCKS);
	}

	public static class ChuckNorrisCommand extends Command {
		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), fetchNewJoke())));
		}

		public String fetchNewJoke() {
			final RestTemplate restTemplate = new RestTemplate();
			final ChuckNorrisJoke root = restTemplate.getForObject("http://api.icndb.com/jokes/random/", ChuckNorrisJoke.class);
			return root.getValue().getJoke();
		}
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

	@Override
	public String getName() {
		return "ChuckNorris Jokes";
	}

	@Override
	public String[] getTriggers() {
		return DEFAULT_CHUCKS;
	}

	@Override
	public String getDescription() {
		return "Displays a random Chuck Norris joke. Powerd by www.icndb.com";
	}

	@Override
	public int getPriority() {
		return EASTEREGGS;
	}
}
