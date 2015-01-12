package com.maciejwalkowiak.mercury.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.Reactor;
import reactor.event.Event;

@Component
class MessengerImpl implements Messenger {
	private final MercuryMessageRepository repository;
	private final Reactor rootReactor;
	private final QueueNameObtainer queueNameObtainer;

	@Autowired
	MessengerImpl(MercuryMessageRepository repository, Reactor rootReactor, QueueNameObtainer queueNameObtainer) {
		this.repository = repository;
		this.rootReactor = rootReactor;
		this.queueNameObtainer = queueNameObtainer;
	}

	@Override
	public MercuryMessage publish(Request request) {
		MercuryMessage message = MercuryMessage.queued(request);
		repository.save(message);
		rootReactor.notify(queueNameObtainer.getQueueName(request), Event.wrap(message));

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
