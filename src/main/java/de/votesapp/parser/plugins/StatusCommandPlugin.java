package de.votesapp.parser.plugins;

import static de.votesapp.parser.Attitude.NEGATIVE;
import static de.votesapp.parser.Attitude.POSITIVE;
import static de.votesapp.parser.Attitude.UNKOWN;
import static org.apache.commons.lang3.StringUtils.trim;

import java.text.MessageFormat;
import java.util.Arrays;
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
import de.votesapp.client.User;
import de.votesapp.groups.Group;
import de.votesapp.parser.Attitude;
import de.votesapp.parser.Command;

@Service
public class StatusCommandPlugin extends AbstractCommandPlugin implements Describable {

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
			final StringBuilder sb = new StringBuilder();

			final Map<Attitude, Integer> sumAttitudes = group.sumAttitudes();

			for (final Attitude attitude : Arrays.asList(POSITIVE, NEGATIVE, UNKOWN)) {
				sb.append(MessageFormat.format("{0}: {1}\n", attitude.getIcon(), sumAttitudes.get(attitude)));

				for (final User user : group.usersWithAttitude(attitude)) {
					sb.append("- " + user.nameOrPhone());

					final Integer additionals = group.getUserAdditionals().getOrDefault(user.getPhone(), 0);

					if (!additionals.equals(0)) {
						sb.append(MessageFormat.format("({0,number,+#;-#})", additionals));
					}
					sb.append("\n");
				}
			}

			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), trim(sb.toString()))));
		}
	}

	@Override
	public String getName() {
		return "Status";
	}

	@Override
	public String[] getTriggers() {
		return new String[] { "Status" };
	}

	@Override
	public String getDescription() {
		return "Displays the status of the current vote";
	}

	@Override
	public int getPriority() {
		return VOTE + 3;
	}
}
