package com.maciejwalkowiak.mercury.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class MessengerImpl implements Messenger {
	private final ApplicationEventPublisher applicationEventPublisher;
	private final MercuryMessageRepository mercuryMessageRepository;

	@Autowired
	MessengerImpl(ApplicationEventPublisher applicationEventPublisher, MercuryMessageRepository mercuryMessageRepository) {
		this.applicationEventPublisher = applicationEventPublisher;

		this.mercuryMessageRepository = mercuryMessageRepository;
	}

	@Override
	public MercuryMessage publish(Request request) {
		MercuryMessage message = MercuryMessage.queued(request);
		mercuryMessageRepository.save(message);

		applicationEventPublisher.publishEvent(new MercuryEvent<>(message));

		return message;
	}
}
