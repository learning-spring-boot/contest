package de.votesapp.parser.commandparser;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

import java.util.Optional;

import de.votesapp.parser.Command;

public abstract class AbstractCommandParser {

	public Optional<Command> parse(final String rawText) {
		return interpret(prepareText(rawText));
	}

	public abstract Optional<Command> interpret(final String text);

	public static String prepareText(final String word) {
		return normalizeSpace(lowerCase(word));
	}
}
