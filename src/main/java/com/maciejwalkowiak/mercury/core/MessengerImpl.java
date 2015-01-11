package com.maciejwalkowiak.mercury.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class MessengerImpl implements Messenger {
	private final ApplicationEventPublisher eventPublisher;
	private final MercuryMessageRepository repository;

	@Autowired
	MessengerImpl(ApplicationEventPublisher eventPublisher, MercuryMessageRepository repository) {
		this.eventPublisher = eventPublisher;
		this.repository = repository;
	}

	@Override
	public MercuryMessage publish(Request request) {
		MercuryMessage message = MercuryMessage.queued(request);
		repository.save(message);

		eventPublisher.publishEvent(new MercuryEvent<>(message));

		return message;
	}

	@Override
	public void messageSent(MercuryMessage message) {
		message.sent();
		repository.save(message);
	}

	@Override
	public void deliveryFailed(MercuryMessage message, String errorMessage) {
		message.failed(errorMessage);
		repository.save(message);
	}
}
