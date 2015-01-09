package de.votesapp.parser;

import static de.votesapp.parser.Attitude.NEGATIVE;
import static de.votesapp.parser.Attitude.POSITIVE;
import static de.votesapp.parser.Attitude.UNKOWN;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import de.votesapp.parser.commandparser.RequestResetCommandParser;
import de.votesapp.parser.commandparser.RequestStatusCommandParser;
import de.votesapp.parser.commandparser.SetAdditionalsCommandParser;
import de.votesapp.parser.commandparser.SetAttitudeCommandParser;

public class HumanMessageParserTest {
	private final String[] positives = SetAttitudeCommandParser.DEFAULT_POSITIVES;
	private final String[] negatives = SetAttitudeCommandParser.DEFAULT_NEGATIVES;
	private final String[] unkowns = SetAttitudeCommandParser.DEFAULT_UNKOWN;
	private final String[] resets = RequestResetCommandParser.DEFAULT_RESETS;

	HumanMessageParser humanMessageParser = new HumanMessageParser(Arrays.asList( //
			new SetAttitudeCommandParser(), //
			new SetAdditionalsCommandParser(), //
			new RequestStatusCommandParser(), //
			new RequestResetCommandParser()));

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
				is(Optional.of(new SetAdditionalsCommandParser.SetAdditionalsCommnad(4))));
	}

	@Test
	public void should_extract_negativ_numbers() throws Exception {
		assertThat(humanMessageParser.parse("-4"), //
				is(Optional.of(new SetAdditionalsCommandParser.SetAdditionalsCommnad(-4))));
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
					is(Optional.of(new SetAttitudeCommandParser.SetAttitudeCommand(POSITIVE))));
		}
	}

	@Test
	public void should_find_all_negatives() throws Exception {
		for (final String negative : negatives) {
			assertThat(negative, //
					humanMessageParser.parse(negative), //
					is(Optional.of(new SetAttitudeCommandParser.SetAttitudeCommand(NEGATIVE))));
		}
	}

	@Test
	public void should_find_all_unkowns() throws Exception {
		for (final String unkown : unkowns) {
			assertThat(humanMessageParser.parse(unkown), //
					is(Optional.of(new SetAttitudeCommandParser.SetAttitudeCommand(UNKOWN))));
		}
	}

	@Test
	public void should_find_all_resets() throws Exception {
		for (final String reset : resets) {
			final Optional<Command> answer = humanMessageParser.parse(reset);

			assertThat(answer.isPresent(), is(true));
			assertThat(answer.get().getClass(), is(typeCompatibleWith(RequestResetCommandParser.RequestResetCommand.class)));
		}
	}

	@Test
	public void should_trim() throws Exception {
		assertThat(humanMessageParser.parse("  +4 "), //
				is(Optional.of(new SetAdditionalsCommandParser.SetAdditionalsCommnad(4))));
	}

	@Test
	public void should_be_case_insensitive() throws Exception {
		assertThat(humanMessageParser.parse("yEs"), //
				is(Optional.of(new SetAttitudeCommandParser.SetAttitudeCommand(POSITIVE))));
	}

	@Test
	public void should_normalize_spaces() throws Exception {
		assertThat(humanMessageParser.parse("  Bin    dabei   "), // I'm in
				is(Optional.of(new SetAttitudeCommandParser.SetAttitudeCommand(POSITIVE))));
	}

	@Test
	public void should_detect_status() throws Exception {
		assertThat(humanMessageParser.parse("Status"), //
				is(Optional.of(RequestStatusCommandParser.RequestStatusCommand.withoutAttitude())));
	}

	// I disabled that because it is currently now supported by the api
	// @Test
	// public void should_detect_status_of_positiv() throws Exception {
	// assertThat(humanMessageParser.parse("Status " + positives[0]), //
	// is(Optional.of(Command.of(StatusRequest.of(Attitude.POSITIVE)))));
	// }
	//
	// @Test
	// public void should_detect_status_of_negative() throws Exception {
	// assertThat(humanMessageParser.parse("Status " + negatives[0]), //
	// is(Optional.of(new
	// SetAttitudeCommandParser.SetAttitudeCommand(NEGATIVE))));
	// }
	//
	// @Test
	// public void should_detect_status_of_unkowns() throws Exception {
	// assertThat(humanMessageParser.parse("Status " + unkowns[0]), //
	// is(Optional.of(new
	// SetAttitudeCommandParser.SetAttitudeCommand(UNKOWN))));
	// }

	@Test
	public void should_detect_status_with_invalid_attitude() throws Exception {
		assertThat(humanMessageParser.parse("Status smthInvalidHere"), //
				is(Optional.of(RequestStatusCommandParser.RequestStatusCommand.withoutAttitude())));
	}
}
