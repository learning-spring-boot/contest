package com.maciejwalkowiak.mercury.core;

/**
 * Responsible for creating messages and changing it's status.
 *
 * @author Maciej Walkowiak
 */
public interface Messenger {
	/**
	 * Creates {@link com.maciejwalkowiak.mercury.core.MercuryMessage} and publishes it into queue for processing
	 *
	 * @param request - request contains message details like content, recipients etc
	 * @return message with status "QUEUED"
	 */
	MercuryMessage publish(Request request);

	/**
	 * Invoked after message has been successfully sent. Changes it's status to "SENT"
	 *
	 * @param message sent message
	 */
	void messageSent(MercuryMessage message);

	/**
	 * Invoked when sending message has failed. Changes it's status to "FAILED" and saves error details
	 * @param message - message that sending has failed
	 * @param errorMessage - error details
	 */
	void deliveryFailed(MercuryMessage message, String errorMessage);
}
