package com.maciejwalkowiak.mercury.mail;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciejwalkowiak.mercury.core.Request;
import org.springframework.mail.SimpleMailMessage;

class SendMailRequest extends Request {
	private final String to;
	private final String content;
	private final String subject;

	@JsonCreator
	public SendMailRequest(@JsonProperty("to") String to, @JsonProperty("content") String content,
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

	public SimpleMailMessage toMailMessage() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setText(content);

		return msg;
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
