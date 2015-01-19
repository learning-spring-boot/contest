package de.votesapp.commands.plugins;

import static de.votesapp.commands.plugins.Attitude.NEGATIVE;
import static de.votesapp.commands.plugins.Attitude.POSITIVE;
import static de.votesapp.commands.plugins.Attitude.UNKOWN;
import static org.apache.commons.lang3.StringUtils.trim;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import de.votesapp.client.GroupMessage;
import de.votesapp.client.User;
import de.votesapp.commands.CommandPlugin;
import de.votesapp.commands.Describable;
import de.votesapp.commands.Description;
import de.votesapp.commands.Description.DescriptionBuilder;
import de.votesapp.groups.Group;

/**
 * Someone ask for the status of the vote.
 *
 * When an {@link #attitude} is present, a list with names which vote for this
 * should be given. It is empty, then a summary should be posted.
 */
@Service
public class StatusCommandPlugin implements CommandPlugin, Describable {

	private static Pattern STATUS = Pattern.compile("status ?(.+)?", Pattern.CASE_INSENSITIVE);

	@Override
	public Optional<Answer> interpret(final GroupMessage message, final Group group) {
		final Matcher statusMatcher = STATUS.matcher(message.normalizedText());
		if (!statusMatcher.matches()) {
			return Optional.empty();
		}

		final String messageToSend = constructMessageToSend(group);

		return Optional.of(Answer.intoGroup(group, messageToSend));
	}

	private String constructMessageToSend(final Group group) {
		final StringBuilder sb = new StringBuilder();

		if (StringUtils.isNotBlank(group.getQuestion())) {
			sb.append("Last Question: " + group.getQuestion() + "\n");
		}

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

		final Duration oneDay = Duration.between(group.getVotingSince().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), LocalDateTime.now());

		final long d = oneDay.toDays();
		final long h = oneDay.toHours() % 24;
		final long m = oneDay.toMinutes() % 60;

		if (d > 0) {
			sb.append(MessageFormat.format("\u00F0\u009F\u0093\u0085: {0}d {1}h:{2}m", d, h, m));
		} else if (h > 0) {
			sb.append(MessageFormat.format("\u00F0\u009F\u0093\u0085: {0}h:{1}m", h, m));
		} else if (m > 0) {
			sb.append(MessageFormat.format("\u00F0\u009F\u0093\u0085: {0}m", m));
		}
		return trim(sb.toString());
	}

	@Override
	public Description describe() {
		return DescriptionBuilder.describe("Status") //
				.withTrigger("Status") //
				.withDescription("Displays the status of the current vote") //
				.onPosition(VOTE + 3) //
				.done();
	}
}
