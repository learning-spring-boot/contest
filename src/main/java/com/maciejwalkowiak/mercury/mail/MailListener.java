package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.Listener;
import net.engio.mbassy.listener.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Listener
class MailListener {
	private static final Logger LOG = LoggerFactory.getLogger(MailListener.class);
	private final MailSender mailSender;

	@Autowired
	MailListener(MailSender javaMailSender) {
		this.mailSender = javaMailSender;
	}

	@Handler
	void sendMail(SendMailRequest sendMailRequest) {
		Assert.notNull(sendMailRequest);

		LOG.info("Received send mail request: {}", sendMailRequest);

		mailSender.send(sendMailRequest.toMailMessage());
	}
}
