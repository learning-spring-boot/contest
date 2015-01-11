package de.votesapp.parser.commandparser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChuckNorrisCommandParserTest {

	@Test
	public void should_fetch_jokes() throws Exception {
		final String joke = new ChuckNorrisCommandParser.ChuckNorrisCommand().fetchNewJoke();

		assertTrue(joke.length() != 0);
	}
}
