package com.maciejwalkowiak.mercury.mail.common;

/**
 * Service used to send emails
 *
 * {@see com.maciejwalkowiak.mercury.mail.javamail.JavaMailMailingService}
 * {@see com.maciejwalkowiak.mercury.mail.sendgrid.SendGridMailingService}
 *
 * @author Maciej Walkowiak
 */
public interface MailingService {
	void send(SendMailRequest sendMailRequest) throws SendMailException;
}
