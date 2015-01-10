package com.maciejwalkowiak.mercury.core;

public class MercuryMessage {
	public enum Status {
		QUEUED,
		SENT
	}

	private final Status status;
	private final Request request;

	private MercuryMessage(Status status, Request request) {
		this.status = status;
		this.request = request;
	}

	public static MercuryMessage queued(Request request) {
		return new MercuryMessage(Status.QUEUED, request);
	}

	public Status getStatus() {
		return status;
	}

	public Request getRequest() {
		return request;
	}
}
