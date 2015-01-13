package com.maciejwalkowiak.mercury.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;

/**
 * Keeps all information about message:
 * - original request
 * - status
 * - and error message if sending failed
 *
 * It's instances are saved in MongoDB by {@link com.maciejwalkowiak.mercury.core.MercuryMessageRepository}
 *
 * @param <T> - request class
 *
 * @author Maciej Walkowiak
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	private String errorMessage;
	private T request;

	/**
	 * Public no-arg controller required by Spring Hateoas
	 */
	public MercuryMessage() {
	}

	private MercuryMessage(Status status, T request) {
		this.status = status;
		this.request = request;
	}

	public static <T extends Request> MercuryMessage queued(T request) {
		return new MercuryMessage<>(Status.QUEUED, request);
	}

	void sent() {
		this.status = Status.SENT;
	}

	void failed(String errorMessage) {
		this.status = Status.FAILED;
		this.errorMessage = errorMessage;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public static class View {
		public interface Summary {}
	}
}
