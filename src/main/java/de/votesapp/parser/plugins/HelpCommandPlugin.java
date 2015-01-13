package de.votesapp.parser.plugins;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class HelpCommandPlugin extends TextEqualsWordPlugin implements Describable {

	public static final String[] DEFAULT_WORDS = { "help" };

	@Autowired
	public HelpCommandPlugin(final List<Describable> describables) {
		super(new HelpCommand(describables), DEFAULT_WORDS);
	}

	public static class HelpCommand extends Command {
		private final String help;

		public HelpCommand(final List<Describable> describables) {
			describables.sort((a, b) -> Integer.compare(a.getPriority(), b.getPriority()));

			final StringBuilder sb = new StringBuilder();
			sb.append("I react on your messages. You can use any of the following commands:\n");

			for (final Describable describable : describables) {
				sb.append("â¡ " + describable.getName() + ":\n");
				sb.append(describable.getDescription() + "\n");

				if (describable.getTriggers().length != 0) {
					for (final String trigger : describable.getTriggers()) {
						sb.append("- " + trigger + "\n");
					}
				}
			}
			sb.append("\nIf you like to stop using VotesApp, kick me out of the room.\n");
			sb.append("\nIf you like to use VotesApp in another room, invite my there.");
			// sb.append("\nTo report bugs, request features or get in touch, just message me directly. I'll forward it.");

			this.help = sb.toString();
		}

		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), help)));
		}
	}

	@Override
	public String getName() {
		return "Help";
	}

	@Override
	public String[] getTriggers() {
		return DEFAULT_WORDS;
	}

	@Override
	public String getDescription() {
		return "Show this help";
	}

	@Override
	public int getPriority() {
		return Integer.MIN_VALUE;
	}

}
