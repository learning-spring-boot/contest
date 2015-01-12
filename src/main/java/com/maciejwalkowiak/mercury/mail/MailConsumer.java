package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.Messenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;
import reactor.event.Event;
import reactor.function.Consumer;

@Component
class MailConsumer implements Consumer<Event<MercuryMessage<SendMailRequest>>> {
	private static final Logger LOG = LoggerFactory.getLogger(MailConsumer.class);
	private final MailSender mailSender;
	private final Messenger messenger;

	@Autowired
	public MailConsumer(MailSender mailSender, Messenger messenger) {
		this.mailSender = mailSender;
		this.messenger = messenger;
	}

	@Override
	public void accept(Event<MercuryMessage<SendMailRequest>> mercuryMessageEvent) {
		MercuryMessage<SendMailRequest> message = mercuryMessageEvent.getData();
		LOG.info("Received send mail request: {}", message.getRequest());

		try {
			mailSender.send(message.getRequest().toMailMessage());
			messenger.messageSent(message);
		} catch (Exception e) {
			messenger.deliveryFailed(message, e.getMessage());
		}
	}
}
