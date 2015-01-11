package com.maciejwalkowiak.mercury.core;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;

public class MercuryMessage<T extends Request> {
	public enum Status {
		QUEUED,
		SENT,
		FAILED
	}

	@Id
	@JsonView(View.Summary.class)
	private String id;
	@JsonView(View.Summary.class)
	private Status status;
	@JsonView(View.Summary.class)
	private String errorMesssage;
	private final T request;

	private MercuryMessage(Status status, T request) {
		this.status = status;
		this.request = request;
	}

	public static <T extends Request> MercuryMessage queued(T request) {
		return new MercuryMessage<>(Status.QUEUED, request);
	}

	public void sent() {
		this.status = Status.SENT;
	}

	public void failed(String errorMessage) {
		this.status = Status.FAILED;
		this.errorMesssage = errorMessage;
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

	public static class View {
		interface Summary {}
	}
}