package de.votesapp.commands.plugins;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import de.votesapp.client.GroupMessage;
import de.votesapp.commands.CommandPlugin;
import de.votesapp.commands.Describable;
import de.votesapp.commands.Description;
import de.votesapp.commands.Description.DescriptionBuilder;
import de.votesapp.groups.Group;

@Service
public class QuestionStoreCommandPlugin implements CommandPlugin, Describable {

	private static Pattern QUESTION = Pattern.compile(".+\\?", Pattern.CASE_INSENSITIVE);

	@Override
	public Optional<Answer> interpret(final GroupMessage message, final Group group) {
		final Matcher statusMatcher = QUESTION.matcher(message.getText());

		if (statusMatcher.matches()) {
			group.setQuestion(message.getText());
		}
		return Optional.empty();
	}

	@Override
	public Description describe() {
		return DescriptionBuilder.describe("Last Question") //
				.withTrigger("Every text that ends with \"?\"") //
				.withDescription("The last question is displayed on vote status") //
				.onPosition(VOTE + 4) //
				.done();
	}

}
