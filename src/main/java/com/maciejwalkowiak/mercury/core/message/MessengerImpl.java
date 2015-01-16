package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.core.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
class MessengerImpl implements Messenger {
	private static final Logger LOG = LoggerFactory.getLogger(MessengerImpl.class);

	private final MessageService messageService;
	private final MessageNotifier messageNotifier;

	@Autowired
	MessengerImpl(MessageService messageService, MessageNotifier messageNotifier) {
		this.messageService = messageService;
		this.messageNotifier = messageNotifier;
	}

	@Override
	public Message publish(Request request) {
		Assert.notNull(request);

		LOG.info("Received request: {}", request);

		Message message = Message.queued(request);
		messageService.save(message);
		messageNotifier.notifyConsumers(message);

		return message;
	}
}
