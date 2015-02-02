package de.votesapp.commands.plugins;

import static de.votesapp.commands.plugins.Attitude.NEGATIVE;
import static de.votesapp.commands.plugins.Attitude.POSITIVE;
import static de.votesapp.commands.plugins.Attitude.UNKOWN;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;

import de.votesapp.client.GroupMessage;
import de.votesapp.commands.CommandPlugin;
import de.votesapp.commands.Describable;
import de.votesapp.commands.Description;
import de.votesapp.commands.Description.DescriptionBuilder;
import de.votesapp.groups.Group;

@Service
public class SetAttitudeCommandPlugin implements CommandPlugin, Describable {

	public static final String[] DEFAULT_POSITIVES = { "ok", "in", "i'm in", "yes", "ja", "bin dabei", Attitude.POSITIVE.getIcon() };
	public static final String[] DEFAULT_NEGATIVES = { "out", "no", "nope", "nein", "bin nicht dabei", "komme nicht", "bin raus", "kann nicht",
			Attitude.NEGATIVE.getIcon() };
	public static final String[] DEFAULT_UNKOWN = { "maybe", "vielleicht", Attitude.UNKOWN.getIcon() };

	private final ImmutableMultimap<String, Attitude> words;

	public SetAttitudeCommandPlugin() {

		// Build an Index over all words that their corresponding attitude.
		final ImmutableMultimap<String, Attitude> words = ImmutableMultimap.<Attitude, String> builder() //
				.putAll(POSITIVE, Arrays.asList(DEFAULT_POSITIVES)) //
				.putAll(NEGATIVE, Arrays.asList(DEFAULT_NEGATIVES)) //
				.putAll(UNKOWN, Arrays.asList(DEFAULT_UNKOWN)) //
				.build().inverse();

		this.words = words;
	}

	@Override
	public Optional<Answer> interpret(final GroupMessage message, final Group group) {
		final ImmutableCollection<Attitude> foundAttitude = words.get(message.normalizedText());

		if (!foundAttitude.isEmpty()) {
			// There is just one by definition.
			final Attitude attitude = foundAttitude.iterator().next();
			group.registerAttitude(message.getSenderPhone(), attitude);
		}

		return Optional.empty();
	}

	@Override
	public Description describe() {
		return DescriptionBuilder.describe("Give a vote") //
				.withTrigger("yes, no, maybe ...") //
				.withDescription("Either you give a vote or it will be counted as maybe.") //
				.onPosition(VOTE + 1) //
				.done();
	}
}
