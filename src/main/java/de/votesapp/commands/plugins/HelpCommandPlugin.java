package de.votesapp.commands.plugins;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import de.votesapp.client.GroupMessage;
import de.votesapp.commands.Describable;
import de.votesapp.commands.Description;
import de.votesapp.commands.Description.DescriptionBuilder;
import de.votesapp.groups.Group;

@Service
public class HelpCommandPlugin extends TextEqualsWordPlugin implements Describable {

	public static final String[] DEFAULT_WORDS = { "help" };
	private final List<Describable> describables;

	@Autowired
	Reactor reactor;

	@Autowired
	public HelpCommandPlugin(final List<Describable> describables) {
		super(DEFAULT_WORDS);
		this.describables = describables;
	}

	@Override
	public Optional<Answer> matches(final GroupMessage message, final Group group) {

		final StringBuilder sb = new StringBuilder();
		sb.append("I react on your messages. You can use any of the following commands:\n");

		// 1. convert Describables into Descriptions
		// 2. sort them by priority
		// 3. append them to the StringBuilder
		describables.stream() //
				.map(describable -> describable.describe()) //
				.sorted((descriptionA, descriptionB) -> Integer.compare(descriptionA.getPriority(), descriptionB.getPriority())) //
				.forEach(description -> appendToSb(description, sb));

		sb.append("\nIf you like to stop using VotesApp, kick me out of the room.\n");
		sb.append("\nIf you like to use VotesApp in another room, invite my there.");
		// sb.append("\nTo report bugs, request features or get in touch, just message me directly. I'll forward it.");

		return Optional.of(Answer.intoGroup(group, sb.toString()));
	}

	private void appendToSb(final Description description, final StringBuilder sb) {
		sb.append("â¡ " + description.getName() + ":\n");
		sb.append(description.getDescription() + "\n");

		if (description.getTrigger().length != 0) {
			for (final String trigger : description.getTrigger()) {
				sb.append("- " + trigger + "\n");
			}
		}
	}

	@Override
	public Description describe() {
		return DescriptionBuilder.describe("Help") //
				.withTrigger(DEFAULT_WORDS) //
				.withDescription("Shows this help") //
				.onPosition(Integer.MIN_VALUE) //
				.done();
	}

}
