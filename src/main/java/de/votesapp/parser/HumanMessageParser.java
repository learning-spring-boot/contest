package de.votesapp.parser;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.votesapp.parser.plugins.AbstractCommandPlugin;

@Component
public class HumanMessageParser {

	private final List<AbstractCommandPlugin> abstractCommandPlugins;

	@Autowired
	public HumanMessageParser(final List<AbstractCommandPlugin> abstractCommandPlugins) {
		this.abstractCommandPlugins = abstractCommandPlugins;
	}

	public Optional<Command> parse(final String rawText) {
		for (final AbstractCommandPlugin abstractCommandPlugin : abstractCommandPlugins) {
			final Optional<Command> command = abstractCommandPlugin.parse(rawText);
			if (command.isPresent()) {
				return command;
			}
		}
		return Optional.empty();
	}
}
