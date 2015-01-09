package de.votesapp.parser;

import reactor.core.Reactor;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;

/**
 * Does nothing. Used for testing
 */
public class NoopCommand extends Command {
	@Override
	public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
	}
}
