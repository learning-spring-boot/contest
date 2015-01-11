package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.MercuryEvent;
import com.maciejwalkowiak.mercury.core.Messenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MailListener implements ApplicationListener<MercuryEvent<SendMailRequest>>{
	private static final Logger LOG = LoggerFactory.getLogger(MailListener.class);
	private final MailSender mailSender;
	private final Messenger messenger;

	@Autowired
	MailListener(MailSender javaMailSender, Messenger messenger) {
		this.mailSender = javaMailSender;
		this.messenger = messenger;
	}

	@Override
	public void onApplicationEvent(MercuryEvent<SendMailRequest> mercuryEvent) {
		Assert.notNull(mercuryEvent);

		LOG.info("Received send mail request: {}", mercuryEvent.getRequest());

		try {
			mailSender.send(mercuryEvent.getRequest().toMailMessage());
			messenger.messageSent(mercuryEvent.getMessage());
		} catch (Exception e) {
			messenger.deliveryFailed(mercuryEvent.getMessage(), e.getMessage());
		}
	}
}
