package de.votesapp.commands.plugins;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.votesapp.client.GroupMessage;
import de.votesapp.commands.Describable;
import de.votesapp.commands.Description;
import de.votesapp.commands.Description.DescriptionBuilder;
import de.votesapp.groups.Group;

@Service
public class ResetCommandPlugin extends TextEqualsWordPlugin implements Describable {

	public static final String[] DEFAULT_RESETS = { "reset", "start", "begin", "vote" };

	public ResetCommandPlugin() {
		super(DEFAULT_RESETS);
	}

	@Override
	public Optional<Answer> matches(final GroupMessage message, final Group group) {
		group.resetVotes();
		return Optional.of(Answer.intoGroup(group, "New Vote started."));
	}

	@Override
	public Description describe() {
		return DescriptionBuilder.describe("Start a new Vote") //
				.withTrigger("vote, begin, start, reset") //
				.withDescription("Starts a new vote. The old answers are deleted.") //
				.onPosition(VOTE + 0) //
				.done();
	}
}
