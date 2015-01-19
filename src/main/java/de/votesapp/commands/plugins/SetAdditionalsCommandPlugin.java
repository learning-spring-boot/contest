package de.votesapp.commands.plugins;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import de.votesapp.client.GroupMessage;
import de.votesapp.commands.Describable;
import de.votesapp.commands.Description;
import de.votesapp.commands.Description.DescriptionBuilder;
import de.votesapp.groups.Group;

@Service
public class SetAdditionalsCommandPlugin implements de.votesapp.commands.CommandPlugin, Describable {

	/**
	 * Matches +1, +2, +42, -1, -40, but not 1, 2, 0, ...
	 */
	private static Pattern NUMBERS = Pattern.compile("[\\+-]?[0-9]+");

	@Override
	public Optional<Answer> interpret(final GroupMessage message, final Group group) {
		if (NUMBERS.matcher(message.normalizedText()).matches()) {
			try {
				final Integer additionals = Integer.parseInt(message.normalizedText());
				group.registerAdditionals(message.getSenderPhone(), additionals);
			} catch (final NumberFormatException nfe) {
				// Number to large, we don't deal with this.
			}
		}
		return Optional.empty();
	}

	@Override
	public Description describe() {
		return DescriptionBuilder.describe("Add additionals") //
				.withTrigger("from -x till +x and 0") //
				.withDescription("If you need bring a friend or so, you could say +2. Then that two are counted *additional* to the vote you did.") //
				.onPosition(VOTE + 2) //
				.done();
	}
}
