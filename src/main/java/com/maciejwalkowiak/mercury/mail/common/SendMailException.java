package com.maciejwalkowiak.mercury.mail.common;

public class SendMailException extends Exception {
	public SendMailException(Throwable cause) {
		super(cause);
	}

	public SendMailException(String message) {
		super(message);
	}
}
