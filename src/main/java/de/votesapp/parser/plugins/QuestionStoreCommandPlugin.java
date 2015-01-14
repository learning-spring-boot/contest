package de.votesapp.parser.plugins;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.parser.Command;

@Service
public class QuestionStoreCommandPlugin extends AbstractCommandPlugin implements Describable {

	private static Pattern QUESTION = Pattern.compile(".+\\?", Pattern.CASE_INSENSITIVE);

	public QuestionStoreCommandPlugin() {
	}

	@Override
	public Optional<Command> interpret(final String text) {
		final Matcher statusMatcher = QUESTION.matcher(text);
		if (!statusMatcher.matches()) {
			return Optional.empty();
		}

		return Optional.of(new QuestionCommand(text));
	}

	public static class QuestionCommand extends Command {

		private final String question;

		public QuestionCommand(final String question) {
			this.question = question;
		}

		@Override
		public void execute(final GroupMessage message, final Group group, final Reactor reactor) {
			group.setQuestion(question);
		}
	}

	@Override
	public String getName() {
		return "Last Question";
	}

	@Override
	public String[] getTriggers() {
		return new String[] { "Every text that ends with \"?\"" };
	}

	@Override
	public String getDescription() {
		return "The last question is displayed on vote status";
	}

	@Override
	public int getPriority() {
		return VOTE + 4;
	}

}
