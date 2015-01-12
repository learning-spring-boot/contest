package com.maciejwalkowiak.mercury.mail.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciejwalkowiak.mercury.core.Request;

public class SendMailRequest extends Request {
	private final String to;
	private final String content;
	private final String subject;

	@JsonCreator
	public SendMailRequest(@JsonProperty("to") String to,
						   @JsonProperty("content") String content,
						   @JsonProperty("subject") String subject) {
		this.to = to;
		this.content = content;
		this.subject = subject;
	}

	public String getTo() {
		return to;
	}

	public String getContent() {
		return content;
	}

	public String getSubject() {
		return subject;
	}

	@Override
	public String toString() {
		return "SendMailRequest{" +
				"to='" + to + '\'' +
				", content='" + content + '\'' +
				", subject='" + subject + '\'' +
				'}';
	}
}
