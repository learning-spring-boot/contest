package de.votesapp.parser;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Someone ask for the status of the vote.
 *
 * When an {@link #attitude} is present, a list with names which vote for this
 * should be given. It is empty, then a summary should be posted.
 */
@EqualsAndHashCode
@Getter
public class StatusRequest {
    private final Optional<Attitude> attitude;

    public static StatusRequest of(Attitude attitude) {
	return new StatusRequest(Optional.of(attitude));
    }

    public static StatusRequest plain() {
	return new StatusRequest(Optional.empty());
    }

    private StatusRequest(Optional<Attitude> attitude) {
	this.attitude = attitude;
    }

}
