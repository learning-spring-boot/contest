package com.maciejwalkowiak.mercury.mail.common;

/**
 * Thrown when anything goes wrong with sending email
 *
 * @author Maciej Walkowiak
 */
public class SendMailException extends Exception {
	public SendMailException(Throwable cause) {
		super(cause);
	}

	public SendMailException(String message) {
		super(message);
	}
}
