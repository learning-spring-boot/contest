package de.votesapp.parser.plugins;

import java.util.Random;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class RollCommandPlugin extends TextEqualsWordPlugin implements Describable {

	public static final String[] DEFAULT_ROLL = { "roll", "dice" };

	public RollCommandPlugin() {
		super(new RollCommand(), DEFAULT_ROLL);
	}

	public static class RollCommand extends Command {
		private final Random random = new Random();

		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), roll() + "â£")));
		}

		public String roll() {
			return String.valueOf(random.nextInt(5) + 1);
		}
	}

	@Override
	public String getName() {
		return "Throw a dice!";
	}

	@Override
	public String[] getTriggers() {
		return new String[] { "roll or dice" };
	}

	@Override
	public String getDescription() {
		return "Gives a random value between 1-6";
	}

	@Override
	public int getPriority() {
		return TOOLS;
	}
}
