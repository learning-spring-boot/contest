package com.maciejwalkowiak.mercury.core.message;

public interface MessageService {
	void save(Message message);

	/**
	 * Invoked after message has been successfully sent. Changes it's status to "SENT"
	 *
	 * @param message sent message
	 */
	void messageSent(Message message);

	/**
	 * Invoked when sending message has failed. Changes it's status to "FAILED" and saves error details
	 * @param message - message that sending has failed
	 * @param errorMessage - error details
	 */
	void deliveryFailed(Message message, String errorMessage);
}
