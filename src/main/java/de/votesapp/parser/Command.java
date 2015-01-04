package de.votesapp.parser;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents a command within a TextMessage of some groupmember.
 *
 * Like: "IN, "YES", "+3", "STATUS", "RESET"
 *
 * Minimum one of {@link #attitude} or {@link #additionals} is non-absent.
 */
@EqualsAndHashCode
@Getter
public class Command {
    // TODO: Use inheritance here. StatusRequestCommand, AttitudeCommand, ...
    private final Optional<StatusRequest> statusRequest;
    private final Optional<Attitude> attitude;
    private final Optional<Integer> additionals;

    public static Command of(StatusRequest statusRequest) {
	return new Command(Optional.of(statusRequest), Optional.empty(),
		Optional.empty());
    }

    public static Command of(Attitude attitude) {
	return new Command(Optional.empty(), Optional.of(attitude),
		Optional.empty());
    }

    public static Command of(Integer additionals) {
	return new Command(Optional.empty(), Optional.empty(),
		Optional.of(additionals));
    }

    private Command(Optional<StatusRequest> statusRequest,
	    Optional<Attitude> attitude, Optional<Integer> additionals) {
	this.statusRequest = statusRequest;
	this.attitude = attitude;
	this.additionals = additionals;
    }
}
