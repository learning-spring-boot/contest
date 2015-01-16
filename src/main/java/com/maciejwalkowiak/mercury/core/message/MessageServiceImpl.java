package com.maciejwalkowiak.mercury.core.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
class MessageServiceImpl implements MessageService {
	private final MessageRepository repository;

	@Autowired
	MessageServiceImpl(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	public void save(Message message) {
		Assert.notNull(message);

		repository.save(message);
	}

	@Override
	public void messageSent(Message message) {
		Assert.notNull(message);

		message.sent();
		repository.save(message);
	}

	@Override
	public void deliveryFailed(Message message, String errorMessage) {
		Assert.notNull(message);

		message.failed(errorMessage);
		repository.save(message);
	}
}
