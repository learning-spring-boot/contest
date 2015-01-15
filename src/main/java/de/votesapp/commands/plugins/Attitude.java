package de.votesapp.commands.plugins;

/**
 * The "Attitude" of a vote describes whether its more {@value #POSITIVE},
 * {@value #NEGATIVE} or {@value #UNKOWN}.
 *
 * That abstraction is used to combine YES and IN behind {@value #POSITIVE} and
 * NO and OUT behind {@value #NEGATIVE} etc.
 */
public enum Attitude {
	// Thumb up
	POSITIVE("\u00f0\u009f\u0091\u008d"),

	// Thumb down
	NEGATIVE("\u00f0\u009f\u0091\u008e"),

	// No fingers (fist)
	UNKOWN("\u00e2\u009c\u008a");

	private final String icon;

	private Attitude(final String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}
}
