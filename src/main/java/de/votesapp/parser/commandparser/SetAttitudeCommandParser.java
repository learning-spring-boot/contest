package de.votesapp.parser.commandparser;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Attitude;
import de.votesapp.parser.Command;

@Service
public class SetAttitudeCommandParser extends AbstractCommandParser implements Describable {

	public static final String[] DEFAULT_POSITIVES = { "in", "I'm in", "yes", "ja", "Bin dabei" };
	public static final String[] DEFAULT_NEGATIVES = { "out", "no", "nope", "nein", "Bin nicht dabei", "Komme nicht" };
	public static final String[] DEFAULT_UNKOWN = { "maybe", "vielleicht" };

	private final TextEqualsWordParser parser;

	public SetAttitudeCommandParser() {

		this.parser = new TextEqualsWordParser.Builder() //
		.with(new SetAttitudeCommand(Attitude.POSITIVE), DEFAULT_POSITIVES) //
		.with(new SetAttitudeCommand(Attitude.NEGATIVE), DEFAULT_NEGATIVES) //
		.with(new SetAttitudeCommand(Attitude.UNKOWN), DEFAULT_UNKOWN) //
		.build();
	}

	@Override
	public Optional<Command> interpret(final String text) {
		return parser.interpret(text);
	}

	@RequiredArgsConstructor
	@Getter
	@EqualsAndHashCode(callSuper = false)
	public static class SetAttitudeCommand extends Command {
		private final Attitude attitude;

		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			group.registerAttitude(message.getSenderPhone(), attitude);

			reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), "Added " + attitude + " vote for " + message.sender().nameOrPhone())));
		}
	}

	@Override
	public String getName() {
		return "Give a vote";
	}

	@Override
	public String[] getTriggers() {
		return new String[] { "yes", "no", "maybe", "..." };
	}

	@Override
	public String getDescription() {
		return "Either you give a vote or it will be counted as maybe.";
	}

	@Override
	public int getPriority() {
		return VOTE + 1;
	}
}
