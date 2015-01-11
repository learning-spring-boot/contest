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
public class MailListener {
	private static final Logger LOG = LoggerFactory.getLogger(MailListener.class);
	private final MailSender mailSender;

	@Autowired
	MailListener(MailSender javaMailSender) {
		this.mailSender = javaMailSender;
	}

	@Handler
	public void sendMail(SendMailMercuryMessage mercuryMessage) {
		Assert.notNull(mercuryMessage);

		LOG.info("Received send mail request: {}", mercuryMessage.getRequest());

		mailSender.send(mercuryMessage.getRequest().toMailMessage());
	}
}
