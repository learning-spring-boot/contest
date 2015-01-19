package de.votesapp.commands.plugins;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import de.votesapp.client.GroupMessage;
import de.votesapp.commands.Describable;
import de.votesapp.commands.Description;
import de.votesapp.commands.Description.DescriptionBuilder;
import de.votesapp.groups.Group;

@Service
public class RollCommandPlugin extends TextEqualsWordPlugin implements Describable {

	public static final String[] DEFAULT_ROLL = { "roll", "dice" };

	private final Random random = new Random();

	public RollCommandPlugin() {
		super(DEFAULT_ROLL);
	}

	@Override
	public Optional<Answer> matches(final GroupMessage message, final Group group) {
		return Optional.of(Answer.intoGroup(group, roll() + "â£"));
	}

	private String roll() {
		return String.valueOf(random.nextInt(5) + 1);
	}

	@Override
	public Description describe() {
		return DescriptionBuilder.describe("Throw a dice!") //
				.withTrigger("roll or dice") //
				.withDescription("Gives a random value between 1-6") //
				.onPosition(TOOLS + 0) //
				.done();
	}
}
