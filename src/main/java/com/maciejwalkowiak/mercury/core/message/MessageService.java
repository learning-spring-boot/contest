package com.maciejwalkowiak.mercury.core.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Maciej Walkowiak
 */
@Service
public class MessageService {
	private final MessageRepository repository;

	@Autowired
	MessageService(MessageRepository repository) {
		this.repository = repository;
	}

	/**
	 * Saves message in data store
	 *
	 * @param message - message to save
	 */
	public void save(Message message) {
		Assert.notNull(message);

		repository.save(message);
	}

	/**
	 * Invoked after message has been successfully sent. Changes it's status to "SENT"
	 *
	 * @param message - sent message
	 */
	public void messageSent(Message message) {
		Assert.notNull(message);

		message.sent();
		repository.save(message);
	}

	/**
	 * Invoked when sending message has failed. Changes it's status to "FAILED" and saves error details
	 *
	 * @param message - message that sending has failed
	 * @param errorMessage - error details
	 */
	public void deliveryFailed(Message message, String errorMessage) {
		Assert.notNull(message);

		message.failed(errorMessage);
		repository.save(message);
	}
}
