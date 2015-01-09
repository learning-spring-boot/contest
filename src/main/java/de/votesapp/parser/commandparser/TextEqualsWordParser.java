package de.votesapp.parser.commandparser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import de.votesapp.parser.Command;

public class TextEqualsWordParser extends AbstractCommandParser {

	// Not really efficient but very easy to code!
	private final Map<String, Command> words;

	/**
	 * @param words
	 *            every word has a command that gets retunred when the word
	 *            equals the text
	 */
	public TextEqualsWordParser(final Map<String, Command> words) {
		this.words = words;
	}

	public TextEqualsWordParser(final Command command, final String... words) {
		final Map<String, Command> map = new HashMap<String, Command>();
		fillAndPrepareMap(map, command, words);
		this.words = map;
	}

	@Override
	public Optional<Command> interpret(final String text) {
		return Optional.ofNullable(words.get(text));
	}

	public static void fillAndPrepareMap(final Map<String, Command> map, final Command command, final String... words) {
		for (final String word : words) {
			map.put(prepareText(word), command);
		}
	}

	public static class Builder {
		private final Map<String, Command> map = new HashMap<String, Command>();

		public Builder with(final Command command, final String... words) {
			fillAndPrepareMap(map, command, words);
			return this;
		}

		public TextEqualsWordParser build() {
			return new TextEqualsWordParser(map);
		}
	}
}
