package de.votesapp.parser.commandparser;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Attitude;
import de.votesapp.parser.Command;

@Service
public class RequestStatusCommandParser extends AbstractCommandParser {

	private static Pattern STATUS = Pattern.compile("status ?(.+)?", Pattern.CASE_INSENSITIVE);

	@Override
	public Optional<Command> interpret(final String text) {
		final Matcher statusMatcher = STATUS.matcher(text);
		if (!statusMatcher.matches()) {
			return Optional.empty();
		}

		return Optional.of(RequestStatusCommand.withoutAttitude());
	}

	/**
	 * Someone ask for the status of the vote.
	 *
	 * When an {@link #attitude} is present, a list with names which vote for
	 * this should be given. It is empty, then a summary should be posted.
	 */
	@EqualsAndHashCode(callSuper = false)
	@Getter
	public static class RequestStatusCommand extends Command {
		private final Optional<Attitude> attitude;

		public static RequestStatusCommand of(final Attitude attitude) {
			return new RequestStatusCommand(Optional.of(attitude));
		}

		public static RequestStatusCommand withoutAttitude() {
			return new RequestStatusCommand(Optional.empty());
		}

		private RequestStatusCommand(final Optional<Attitude> attitude) {
			this.attitude = attitude;
		}

		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			final StringBuilder statusMessage = new StringBuilder();

			final Map<Attitude, Integer> sumAttitudes = group.sumAttitudes();
			statusMessage.append("*thumb_up*: ").append(sumAttitudes.get(Attitude.POSITIVE)).append("\n");
			statusMessage.append("*thumb_down*: ").append(sumAttitudes.get(Attitude.NEGATIVE)).append("\n");
			statusMessage.append("*questionmark*: ").append(sumAttitudes.get(Attitude.UNKOWN));

			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), statusMessage.toString())));
		}
	}
}
