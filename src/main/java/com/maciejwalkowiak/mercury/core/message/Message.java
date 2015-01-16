package com.maciejwalkowiak.mercury.core.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maciejwalkowiak.mercury.core.Request;
import org.springframework.data.annotation.Id;

/**
 * Keeps all information about message:
 * - original request
 * - status
 * - and error message if sending failed
 *
 * It's instances are saved in MongoDB by {@link MessageRepository}
 *
 * @param <T> - request class
 *
 * @author Maciej Walkowiak
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message<T extends Request> {
	public enum Status {
		QUEUED,
		SENT,
		FAILED
	}

	@Id
	private String id;
	private Status status;
	private String errorMessage;
	private T request;

	/**
	 * Public no-arg controller required by Spring Hateoas
	 */
	public Message() {
	}

	private Message(Status status, T request) {
		this.status = status;
		this.request = request;
	}

	public static <T extends Request> Message queued(T request) {
		return new Message<>(Status.QUEUED, request);
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
}
