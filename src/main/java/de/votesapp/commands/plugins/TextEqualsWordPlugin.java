package de.votesapp.commands.plugins;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import de.votesapp.client.GroupMessage;
import de.votesapp.commands.CommandPlugin;
import de.votesapp.commands.TextNormalizer;
import de.votesapp.groups.Group;

public abstract class TextEqualsWordPlugin implements CommandPlugin {

	private final Set<String> words = new HashSet<>();

	public TextEqualsWordPlugin(final String... words) {
		for (final String word : words) {
			this.words.add(TextNormalizer.normalize(word));
		}
	}

	@Override
	public Optional<Answer> interpret(final GroupMessage message, final Group group) {
		if (words.contains(message.normalizedText())) {
			return matches(message, group);
		}
		return Optional.empty();
	}

	abstract Optional<Answer> matches(final GroupMessage message, final Group group);
}
