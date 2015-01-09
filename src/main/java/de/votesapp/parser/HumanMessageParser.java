package de.votesapp.parser;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.votesapp.parser.commandparser.AbstractCommandParser;

@Component
public class HumanMessageParser {

	private final List<AbstractCommandParser> abstractCommandParsers;

	@Autowired
	public HumanMessageParser(final List<AbstractCommandParser> abstractCommandParsers) {
		this.abstractCommandParsers = abstractCommandParsers;
	}

	public Optional<Command> parse(final String rawText) {
		for (final AbstractCommandParser abstractCommandParser : abstractCommandParsers) {
			final Optional<Command> command = abstractCommandParser.parse(rawText);
			if (command.isPresent()) {
				return command;
			}
		}
		return Optional.empty();
	}
}
