package de.votesapp.parser;

/**
 * The "Attitude" of a vote describes whether its more {@value #POSITIVE},
 * {@value #NEGATIVE} or {@value #UNKOWN}.
 *
 * That abstraction is used to combine YES and IN behind {@value #POSITIVE} and
 * NO and OUT behind {@value #NEGATIVE} etc.
 */
public enum Attitude {
    POSITIVE, NEGATIVE, UNKOWN
}
