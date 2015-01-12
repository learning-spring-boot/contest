package com.maciejwalkowiak.mercury.mail.common;

import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.Messenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.event.Event;
import reactor.function.Consumer;

@Component
class MailConsumer implements Consumer<Event<MercuryMessage<SendMailRequest>>> {
	private static final Logger LOG = LoggerFactory.getLogger(MailConsumer.class);
	private final MailingService mailingService;
	private final Messenger messenger;

	@Autowired
	public MailConsumer(MailingService mailingService, Messenger messenger) {
		this.mailingService = mailingService;
		this.messenger = messenger;
	}

	@Override
	public void accept(Event<MercuryMessage<SendMailRequest>> mercuryMessageEvent) {
		MercuryMessage<SendMailRequest> message = mercuryMessageEvent.getData();
		LOG.info("Received send mail request: {}", message.getRequest());

		try {
			mailingService.send(message.getRequest());
			messenger.messageSent(message);
		} catch (SendMailException e) {
			messenger.deliveryFailed(message, e.getMessage());
		}
	}
}
