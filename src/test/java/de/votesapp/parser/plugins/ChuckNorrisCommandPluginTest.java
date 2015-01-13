package de.votesapp.parser.plugins;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.votesapp.parser.plugins.ChuckNorrisCommandPlugin;

public class ChuckNorrisCommandPluginTest {

	@Test
	public void should_fetch_jokes() throws Exception {
		final String joke = new ChuckNorrisCommandPlugin.ChuckNorrisCommand().fetchNewJoke();

		assertTrue(joke.length() != 0);
	}
}
