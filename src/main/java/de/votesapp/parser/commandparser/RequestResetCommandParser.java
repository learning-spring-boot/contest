package de.votesapp.parser.commandparser;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class RequestResetCommandParser extends TextEqualsWordParser {

	public static final String[] DEFAULT_RESETS = { "reset", "neu", "zurücksetzten", "neustarten", "start", "starten", "begin" };

	public RequestResetCommandParser() {
		super(new RequestResetCommand(), DEFAULT_RESETS);
	}

	public static class RequestResetCommand extends Command {
		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			group.resetVotes();
		}
	}
}
