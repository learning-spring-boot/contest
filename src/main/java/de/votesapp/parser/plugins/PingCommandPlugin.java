package de.votesapp.parser.plugins;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class PingCommandPlugin extends TextEqualsWordPlugin {

	public static final String[] DEFAULT_RESETS = { "ping", "ping?" };

	public PingCommandPlugin() {
		super(new RequestResetCommand(), DEFAULT_RESETS);
	}

	public static class RequestResetCommand extends Command {
		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			group.resetVotes();
			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), "Pong!")));
		}
	}
}
