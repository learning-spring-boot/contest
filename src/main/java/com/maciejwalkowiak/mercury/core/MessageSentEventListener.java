package com.maciejwalkowiak.mercury.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
class MessageSentEventListener implements ApplicationListener<MessageSentEvent> {
	private final MercuryMessageRepository mercuryMessageRepository;

	@Autowired
	MessageSentEventListener(MercuryMessageRepository mercuryMessageRepository) {
		this.mercuryMessageRepository = mercuryMessageRepository;
	}

	@Override
	public void onApplicationEvent(MessageSentEvent event) {
		MercuryMessage message = event.getMercuryMessage();
		message.sent();

		mercuryMessageRepository.save(message);
	}
}
