package com.maciejwalkowiak.mercury.mail.common;

import com.maciejwalkowiak.mercury.core.message.MessageConsumer;
import com.maciejwalkowiak.mercury.core.message.Message;
import com.maciejwalkowiak.mercury.core.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Consumes incoming {@link com.maciejwalkowiak.mercury.mail.common.SendMailRequest} based events
 * and sends emails through configured provider ({@link com.maciejwalkowiak.mercury.mail.common.MailingService}).
 *
 * If there is no provider configured all incoming emails will get status "FAILED"
 *
 * @author Maciej Walkowiak
 */
@Component
class MailConsumer implements MessageConsumer<SendMailRequest> {
	private static final Logger LOG = LoggerFactory.getLogger(MailConsumer.class);

	private final Optional<MailingService> mailingService;
	private final MessageService messageService;

	@Autowired
	public MailConsumer(Optional<MailingService> mailingService, MessageService messageService) {
		this.mailingService = mailingService;
		this.messageService = messageService;
	}

	@Override
	public void consume(Message<SendMailRequest> message) {
		LOG.info("Received send mail request: {}", message.getRequest());

		if (mailingService.isPresent()) {
			try {
				mailingService.get().send(message.getRequest());
				messageService.messageSent(message);
			} catch (SendMailException e) {
				messageService.deliveryFailed(message, e.getMessage());
			}
		} else {
			messageService.deliveryFailed(message, "Mailing provider is not configured");
		}
	}
}
