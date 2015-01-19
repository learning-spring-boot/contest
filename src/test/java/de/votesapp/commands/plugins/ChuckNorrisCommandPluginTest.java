package de.votesapp.commands.plugins;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.votesapp.commands.plugins.ChuckNorrisCommandPlugin;

public class ChuckNorrisCommandPluginTest {

	@Test
	public void should_fetch_jokes() throws Exception {
		final String joke = new ChuckNorrisCommandPlugin().fetchNewJoke();

		assertTrue(joke.length() != 0);
	}
}
