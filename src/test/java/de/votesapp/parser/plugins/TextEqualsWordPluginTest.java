package de.votesapp.parser.plugins;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

import de.votesapp.parser.Command;
import de.votesapp.parser.NoopCommand;
import de.votesapp.parser.plugins.TextEqualsWordPlugin;

public class TextEqualsWordPluginTest {

	TextEqualsWordPlugin textEqualsWordPlugin;

	@Test
	public void should_find_words_via_builder() throws Exception {
		final Command first = new NoopCommand();
		final Command second = new NoopCommand();

		textEqualsWordPlugin = new TextEqualsWordPlugin.Builder() //
				.with(first, "Hello") //
				.with(second, "World") //
				.build();

		// if they would be the same, the following asserts will not match
		assertThat(first, is(not(second)));

		assertThat(textEqualsWordPlugin.parse("Hello"), is(Optional.of(first)));
		assertThat(textEqualsWordPlugin.parse("World"), is(Optional.of(second)));
		assertThat(textEqualsWordPlugin.parse("Magic"), is(Optional.empty()));
	}

	@Test
	public void should_find_word() throws Exception {
		final Command command = new NoopCommand();

		textEqualsWordPlugin = new TextEqualsWordPlugin(command, "Magic");

		assertThat(textEqualsWordPlugin.parse("Magic"), is(Optional.of(command)));
	}

	@Test
	public void should_not_find_word() throws Exception {
		final Command command = new NoopCommand();

		textEqualsWordPlugin = new TextEqualsWordPlugin(command, "Magic");

		assertThat(textEqualsWordPlugin.parse("Hello World"), is(Optional.empty()));
	}
}
