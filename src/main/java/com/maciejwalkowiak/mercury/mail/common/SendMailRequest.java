package com.maciejwalkowiak.mercury.mail.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciejwalkowiak.mercury.core.Request;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Data structure containing all data needed to send email
 *
 * @author Maciej Walkowiak
 */
public class SendMailRequest extends Request {
	@NotEmpty
	private final List<String> to;
	private final List<String> cc;
	private final List<String> bcc;
	@NotNull
	private final String text;
	@NotNull
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
		return to == null ? new ArrayList<>() : to;
	}

	public String getText() {
		return text;
	}

	public String getSubject() {
		return subject;
	}

	public List<String> getCc() {
		return cc == null ? new ArrayList<>() : cc;
	}

	public List<String> getBcc() {
		return bcc == null ? new ArrayList<>() : bcc;
	}

	@Override
	public String toString() {
		return "SendMailRequest{" +
				"to=" + to +
				", cc=" + cc +
				", bcc=" + bcc +
				", text='" + text + '\'' +
				", subject='" + subject + '\'' +
				'}';
	}
}
