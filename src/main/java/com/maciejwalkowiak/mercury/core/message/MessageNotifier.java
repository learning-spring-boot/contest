package com.maciejwalkowiak.mercury.core.message;

/**
 * Notifies consumers about incoming messages
 *
 * @author Maciej Walkowiak
 */
public interface MessageNotifier {
	void notifyConsumers(Message<?> message);
}
