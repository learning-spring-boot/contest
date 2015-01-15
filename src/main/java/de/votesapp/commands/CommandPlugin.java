package de.votesapp.commands;

import java.util.Optional;

import de.votesapp.client.GroupMessage;
import de.votesapp.commands.plugins.Answer;
import de.votesapp.groups.Group;

public interface CommandPlugin {
	public abstract Optional<Answer> interpret(final GroupMessage message, final Group group);
}
