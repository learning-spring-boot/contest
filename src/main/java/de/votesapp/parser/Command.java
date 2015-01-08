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
	private final boolean reset;

	public static Command of(final boolean reset) {
		return new Command(Optional.empty(), Optional.empty(), Optional.empty(), reset);
	}

	public static Command of(final StatusRequest statusRequest) {
		return new Command(Optional.of(statusRequest), Optional.empty(), Optional.empty(), false);
	}

	public static Command of(final Attitude attitude) {
		return new Command(Optional.empty(), Optional.of(attitude), Optional.empty(), false);
	}

	public static Command of(final Integer additionals) {
		return new Command(Optional.empty(), Optional.empty(), Optional.of(additionals), false);
	}

	private Command(final Optional<StatusRequest> statusRequest, final Optional<Attitude> attitude, final Optional<Integer> additionals, final boolean reset) {
		this.statusRequest = statusRequest;
		this.attitude = attitude;
		this.additionals = additionals;
		this.reset = reset;
	}
}
