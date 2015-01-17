package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.core.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Responsible for creating messages and notifying consumers about them.
 *
 * @author Maciej Walkowiak
 */
@Component
public class Messenger {
	private static final Logger LOG = LoggerFactory.getLogger(Messenger.class);

	private final MessageService messageService;
	private final MessageNotifier messageNotifier;

	@Autowired
	Messenger(MessageService messageService, MessageNotifier messageNotifier) {
		this.messageService = messageService;
		this.messageNotifier = messageNotifier;
	}

	/**
	 * Creates {@link Message} and publishes it into queue for processing
	 *
	 * @param request - request contains message details like content, recipients etc
	 * @return message with status "QUEUED"
	 */
	public Message publish(Request request) {
		Assert.notNull(request);

		LOG.info("Received request: {}", request);

		Message message = Message.queued(request);
		messageService.save(message);
		messageNotifier.notifyConsumers(message);

		return message;
	}
}
