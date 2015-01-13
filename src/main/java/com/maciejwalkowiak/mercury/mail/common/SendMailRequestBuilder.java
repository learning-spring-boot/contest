package com.maciejwalkowiak.mercury.mail.common;

import java.util.ArrayList;
import java.util.List;

public class SendMailRequestBuilder {
	private List<String> to = new ArrayList<>();
	private List<String> cc = new ArrayList<>();;
	private List<String> bcc = new ArrayList<>();;
	private String subject;
	private String text;

	public SendMailRequestBuilder to(String to) {
		this.to.add(to);
		return this;
	}

	public SendMailRequestBuilder cc(String cc) {
		this.cc.add(cc);
		return this;
	}

	public SendMailRequestBuilder bcc(String bcc) {
		this.bcc.add(bcc);
		return this;
	}

	public SendMailRequestBuilder subject(String subject) {
		this.subject = subject;
		return this;
	}

	public SendMailRequestBuilder text(String text) {
		this.text = text;
		return this;
	}

	public SendMailRequest build() {
		return new SendMailRequest(to, cc, bcc, subject, text);
	}
}
