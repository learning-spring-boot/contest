package de.votesapp.parser;

import reactor.core.Reactor;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;

/**
 * Represents a command that we received of some member of a group.
 *
 * Like: "IN, "YES", "+3", "STATUS", "RESET"
 */
public abstract class Command {
	public abstract void execute(final GroupMessage message, final Group group, final Reactor reactor);
}
