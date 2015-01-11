package de.votesapp.parser.commandparser;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class RequestResetCommandParser extends TextEqualsWordParser implements Describable {

	public static final String[] DEFAULT_RESETS = { "reset", "start", "begin", "vote" };

	public RequestResetCommandParser() {
		super(new RequestResetCommand(), DEFAULT_RESETS);
	}

	public static class RequestResetCommand extends Command {
		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			group.resetVotes();
			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), "New Vote started.")));
		}
	}

	@Override
	public int getPriority() {
		return VOTE + 0;
	}

	@Override
	public String getName() {
		return "Start a new Vote";
	}

	@Override
	public String[] getTriggers() {
		return DEFAULT_RESETS;
	}

	@Override
	public String getDescription() {
		return "Starts a new vote. The old answers are deleted.";
	}
}
