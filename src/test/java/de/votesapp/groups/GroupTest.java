package de.votesapp.groups;

import static de.votesapp.parser.Attitude.NEGATIVE;
import static de.votesapp.parser.Attitude.POSITIVE;
import static de.votesapp.parser.Attitude.UNKOWN;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import de.votesapp.parser.Attitude;

public class GroupTest {

	@Test
	public void should_count_attitudes() throws Exception {
		final Group group = new Group("foo");

		group.registerAttitude("a", POSITIVE);
		group.registerAttitude("b", NEGATIVE);
		group.registerAttitude("c", POSITIVE);

		final Map<Attitude, Integer> sumAttitudes = group.sumAttitudes();

		assertThat(sumAttitudes.get(POSITIVE), is(2));
		assertThat(sumAttitudes.get(NEGATIVE), is(1));
		assertThat(sumAttitudes.get(UNKOWN), is(0));
	}

	@Test
	public void should_count_attitudes_and_additionals() throws Exception {
		final Group group = new Group("foo");

		group.registerAttitude("a", POSITIVE);
		group.registerAttitude("b", NEGATIVE);

		group.registerAdditionals("a", 2);

		final Map<Attitude, Integer> sumAttitudes = group.sumAttitudes();

		assertThat(sumAttitudes.get(POSITIVE), is(3));
		assertThat(sumAttitudes.get(NEGATIVE), is(1));
		assertThat(sumAttitudes.get(UNKOWN), is(0));
	}

	@Test
	public void should_reset_votes() throws Exception {
		final Group group = new Group("foo");

		group.registerAttitude("a", POSITIVE);
		group.registerAttitude("b", NEGATIVE);
		group.registerAttitude("c", UNKOWN);
		group.registerAdditionals("a", 2);

		group.resetVotes();

		final Map<Attitude, Integer> sumAttitudes = group.sumAttitudes();

		assertThat(sumAttitudes.get(POSITIVE), is(0));
		assertThat(sumAttitudes.get(NEGATIVE), is(0));
		assertThat(sumAttitudes.get(UNKOWN), is(3));
	}
}
