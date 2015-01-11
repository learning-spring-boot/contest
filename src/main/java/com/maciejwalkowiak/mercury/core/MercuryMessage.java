package com.maciejwalkowiak.mercury.core;

import org.springframework.data.annotation.Id;

public class MercuryMessage<T extends Request> {
	public enum Status {
		QUEUED,
		SENT
	}

	@Id
	private String id;
	private final Status status;
	private final T request;

	protected MercuryMessage(Status status, T request) {
		this.status = status;
		this.request = request;
	}

	public static <T extends Request> MercuryMessage queued(T request) {
		return new MercuryMessage<>(Status.QUEUED, request);
	}

	public Status getStatus() {
		return status;
	}

	public T getRequest() {
		return request;
	}

	public String getId() {
		return id;
	}
}
