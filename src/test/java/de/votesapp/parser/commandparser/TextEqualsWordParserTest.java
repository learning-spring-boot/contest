package de.votesapp.parser.commandparser;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import de.votesapp.parser.Command;
import de.votesapp.parser.NoopCommand;

public class TextEqualsWordParserTest {

	TextEqualsWordParser textEqualsWordParser;

	@Test
	public void should_find_words_via_builder() throws Exception {
		final Command first = new NoopCommand();
		final Command second = new NoopCommand();

		textEqualsWordParser = new TextEqualsWordParser.Builder() //
				.with(first, "Hello") //
				.with(second, "World") //
				.build();

		// if they would be the same, the following asserts will not match
		assertThat(first, is(not(second)));

		assertThat(textEqualsWordParser.parse("Hello"), is(Optional.of(first)));
		assertThat(textEqualsWordParser.parse("World"), is(Optional.of(second)));
		assertThat(textEqualsWordParser.parse("Magic"), is(Optional.empty()));
	}

	@Test
	public void should_find_word() throws Exception {
		final Command command = new NoopCommand();

		textEqualsWordParser = new TextEqualsWordParser(command, "Magic");

		assertThat(textEqualsWordParser.parse("Magic"), is(Optional.of(command)));
	}

	@Test
	public void should_not_find_word() throws Exception {
		final Command command = new NoopCommand();

		textEqualsWordParser = new TextEqualsWordParser(command, "Magic");

		assertThat(textEqualsWordParser.parse("Hello World"), is(Optional.empty()));
	}
}
