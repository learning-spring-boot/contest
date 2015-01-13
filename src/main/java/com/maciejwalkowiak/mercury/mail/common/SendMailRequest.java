package com.maciejwalkowiak.mercury.mail.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciejwalkowiak.mercury.core.Request;

import java.util.List;

/**
 * Data structure containing all data needed to send email
 *
 * @author Maciej Walkowiak
 */
public class SendMailRequest extends Request {
	private final List<String> to;
	private final List<String> cc;
	private final List<String> bcc;
	private final String text;
	private final String subject;

	@JsonCreator
	public SendMailRequest(@JsonProperty("to") List<String> to,
						   @JsonProperty("cc") List<String> cc,
						   @JsonProperty("bcc") List<String> bcc,
						   @JsonProperty("text") String text,
						   @JsonProperty("subject") String subject) {
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.text = text;
		this.subject = subject;
	}

	public List<String> getTo() {
		return to;
	}

	public String getText() {
		return text;
	}

	public String getSubject() {
		return subject;
	}

	public List<String> getCc() {
		return cc;
	}

	public List<String> getBcc() {
		return bcc;
	}

	@Override
	public String toString() {
		return "SendMailRequest{" +
				"to='" + to + '\'' +
				", text='" + text + '\'' +
				", subject='" + subject + '\'' +
				'}';
	}
}
