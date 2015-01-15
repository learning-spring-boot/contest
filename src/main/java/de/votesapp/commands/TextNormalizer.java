package de.votesapp.commands;

import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

public final class TextNormalizer {

	private TextNormalizer() {
		// utility
	}

	public static String normalize(final String text) {
		return normalizeSpace(lowerCase(text));
	}

}
