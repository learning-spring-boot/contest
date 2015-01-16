package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.core.Request;

/**
 * Responsible for creating messages and notifying consumers about them.
 *
 * @author Maciej Walkowiak
 */
public interface Messenger {
	/**
	 * Creates {@link Message} and publishes it into queue for processing
	 *
	 * @param request - request contains message details like content, recipients etc
	 * @return message with status "QUEUED"
	 */
	Message publish(Request request);
}
