package de.votesapp.parser.commandparser;

import java.util.Optional;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class SetAdditionalsCommandParser extends AbstractCommandParser implements Describable {

	/**
	 * Matches +1, +2, +42, -1, -40, but not 1, 2, 0, ...
	 */
	private static Pattern NUMBERS = Pattern.compile("[\\+-]?([0-9])+");

	@Override
	public Optional<Command> interpret(final String text) {
		if (NUMBERS.matcher(text).matches()) {
			try {
				final Integer additionals = Integer.parseInt(text);
				return Optional.of(new SetAdditionalsCommnad(additionals));
			} catch (final NumberFormatException nfe) {
				// Number to large, we don't deal with this.
			}
		}

		return Optional.empty();
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public static class SetAdditionalsCommnad extends Command {
		private final Integer additionals;

		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			group.registerAdditionals(message.getSender(), additionals);
		}
	}

	@Override
	public String getName() {
		return "Add additionals";
	}

	@Override
	public String[] getTriggers() {
		return new String[] { "from -x till +x and 0" };
	}

	@Override
	public String getDescription() {
		return "If you need bring a friend or so, you could say +2. Then that two are counted *additional* to the vote you did.";
	}

	@Override
	public int getPriority() {
		return VOTE + 2;
	}
}
