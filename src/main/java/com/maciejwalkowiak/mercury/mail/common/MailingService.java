package com.maciejwalkowiak.mercury.mail.common;

public interface MailingService {
	void send(SendMailRequest sendMailRequest) throws SendMailException;
}
