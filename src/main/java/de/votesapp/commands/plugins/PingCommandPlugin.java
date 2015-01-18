package de.votesapp.commands.plugins;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;

@Service
public class PingCommandPlugin extends TextEqualsWordPlugin {

	public static final String[] DEFAULT_RESETS = { "ping", "ping?" };

	public PingCommandPlugin() {
		super(DEFAULT_RESETS);
	}

	@Override
	public Optional<Answer> matches(final GroupMessage message, final Group group) {
		return Optional.of(Answer.intoGroup(group, "Pong!"));
	}

}
