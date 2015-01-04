package de.votesapp.parser;

import static de.votesapp.parser.Attitude.NEGATIVE;
import static de.votesapp.parser.Attitude.POSITIVE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

public class HumanMessageParserTest {
	private final String[] positives = HumanMessageParser.DEFAULT_POSITIVES;
	private final String[] negatives = HumanMessageParser.DEFAULT_NEGATIVES;
	private final String[] unkowns = HumanMessageParser.DEFAULT_UNKOWN;

	HumanMessageParser humanMessageParser = new HumanMessageParser(positives, negatives, unkowns);

	@Test
	public void should_be_quite_on_normal_test() throws Exception {
		assertThat(humanMessageParser.parse("In winter it snows"), //
				is(Optional.empty()));
	}

	@Test
	public void should_not_find_numbers_surronded_by_text() throws Exception {
		assertThat(humanMessageParser.parse("It's currently +4 Degress "), //
				is(Optional.empty()));
	}

	@Test
	public void should_extract_positiv_numbers() throws Exception {
		assertThat(humanMessageParser.parse("+4"), //
				is(Optional.of(Command.of(4))));
	}

	@Test
	public void should_extract_negativ_numbers() throws Exception {
		assertThat(humanMessageParser.parse("-4"), //
				is(Optional.of(Command.of(-4))));
	}

	@Test
	public void should_not_fail_on_large_numbers() throws Exception {
		assertThat(humanMessageParser.parse("+9999999999"), //
				is(Optional.empty()));
	}

	@Test
	public void should_find_all_positives() throws Exception {
		for (final String positive : positives) {
			assertThat(humanMessageParser.parse(positive), //
					is(Optional.of(Command.of(POSITIVE))));
		}
	}

	@Test
	public void should_find_all_negatives() throws Exception {
		for (final String negative : negatives) {
			assertThat(humanMessageParser.parse(negative), //
					is(Optional.of(Command.of(NEGATIVE))));
		}
	}

	@Test
	public void should_find_all_unkowns() throws Exception {
		for (final String unkown : unkowns) {
			assertThat(humanMessageParser.parse(unkown), //
					is(Optional.of(Command.of(Attitude.UNKOWN))));
		}
	}

	@Test
	public void should_trim() throws Exception {
		assertThat(humanMessageParser.parse("  +4 "), //
				is(Optional.of(Command.of(4))));
	}

	@Test
	public void should_normalize_spaces() throws Exception {
		assertThat(humanMessageParser.parse("  Bin    dabei   "), // I'm in
				is(Optional.of(Command.of(POSITIVE))));
	}

	@Test
	public void should_detect_status() throws Exception {
		assertThat(humanMessageParser.parse("Status"), //
				is(Optional.of(Command.of(StatusRequest.plain()))));
	}

	@Test
	public void should_detect_status_of_positiv() throws Exception {
		assertThat(humanMessageParser.parse("Status " + positives[0]), //
				is(Optional.of(Command.of(StatusRequest.of(Attitude.POSITIVE)))));
	}

	@Test
	public void should_detect_status_of_negative() throws Exception {
		assertThat(humanMessageParser.parse("Status " + negatives[0]), //
				is(Optional.of(Command.of(StatusRequest.of(Attitude.NEGATIVE)))));
	}

	@Test
	public void should_detect_status_of_unkowns() throws Exception {
		assertThat(humanMessageParser.parse("Status " + unkowns[0]), //
				is(Optional.of(Command.of(StatusRequest.of(Attitude.UNKOWN)))));
	}

	@Test
	public void should_detect_status_with_invalid_attitude() throws Exception {
		assertThat(humanMessageParser.parse("Status smthInvalidHere"), //
				is(Optional.of(Command.of(StatusRequest.plain()))));
	}
}
