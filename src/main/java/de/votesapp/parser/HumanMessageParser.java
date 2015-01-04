package de.votesapp.parser;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class HumanMessageParser {

    static final String[] DEFAULT_POSITIVES = { "in", "I'm in", "yes", "ja",
	    "Bin dabei" };

    static final String[] DEFAULT_NEGATIVES = { "out", "no", "nein",
	    "Bin nicht dabei", "Komme nicht" };

    static final String[] DEFAULT_UNKOWN = { "maybe", "vielleicht" };

    /**
     * Matches +1, +2, +42, -1, -40, but not 1, 2, 0, ...
     */
    private static Pattern NUMBERS = Pattern.compile("[\\+-]([0-9])+");

    private static Pattern STATUS = Pattern.compile("status ?(.+)?",
	    Pattern.CASE_INSENSITIVE);

    private final String[] positives;
    private final String[] negatives;
    private final String[] unkowns;

    public Optional<Command> parse(String rawText) {
	String text = StringUtils.normalizeSpace(rawText);

	Optional<Integer> foundAdditionals = extractAdditionals(text);
	if (foundAdditionals.isPresent()) {
	    return Optional.of(Command.of(foundAdditionals.get()));
	}

	Optional<Attitude> foundAttitude = extractAttitude(text);
	if (foundAttitude.isPresent()) {
	    return Optional.of(Command.of(foundAttitude.get()));
	}

	Optional<StatusRequest> statusRequest = extractStatusRequest(text);
	if (statusRequest.isPresent()) {
	    return Optional.of(Command.of(statusRequest.get()));
	}

	return Optional.empty();
    }

    private Optional<Integer> extractAdditionals(String text) {
	if (NUMBERS.matcher(text).matches()) {
	    try {
		Integer additionals = Integer.parseInt(text);
		return Optional.of(additionals);
	    } catch (NumberFormatException nfe) {
		// Number to large, we don't deal with this.
	    }
	}

	return Optional.empty();
    }

    private Optional<Attitude> extractAttitude(@Nullable String text) {

	if (text == null) {
	    return Optional.empty();
	}

	if (textEqualsOneOf(text, positives)) {
	    return Optional.of(Attitude.POSITIVE);
	}

	if (textEqualsOneOf(text, negatives)) {
	    return Optional.of(Attitude.NEGATIVE);
	}

	if (textEqualsOneOf(text, unkowns)) {
	    return Optional.of(Attitude.UNKOWN);
	}

	return Optional.empty();
    }

    private Optional<StatusRequest> extractStatusRequest(String text) {
	Matcher statusMatcher = STATUS.matcher(text);
	if (!statusMatcher.matches()) {
	    return Optional.empty();
	}

	Optional<Attitude> attitude = extractAttitude(statusMatcher.group(1));

	if (attitude.isPresent()) {
	    return Optional.of(StatusRequest.of(attitude.get()));
	} else {
	    return Optional.of(StatusRequest.plain());
	}
    }

    private boolean textEqualsOneOf(String text, String[] possibilities) {
	for (String posibility : possibilities) {
	    if (text.equalsIgnoreCase(posibility)) {
		return true;
	    }
	}
	return false;
    }
}