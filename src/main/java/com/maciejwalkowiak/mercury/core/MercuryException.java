package com.maciejwalkowiak.mercury.core;

import org.springframework.mail.MailException;

public class MercuryException extends RuntimeException {
	public MercuryException(MailException e) {
		super(e);
	}
}
