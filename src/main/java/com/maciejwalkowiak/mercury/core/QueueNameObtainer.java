package com.maciejwalkowiak.mercury.core;

import org.springframework.stereotype.Component;

/**
 * Queue name needs to be known when message is sent to queue and also when listener subscribes to queue.
 * QueueNameObtainer returns same queue name for object and for it's class.
 *
 * See {@link com.maciejwalkowiak.mercury.core.MessengerImpl}
 * and {@link com.maciejwalkowiak.mercury.mail.common.MailConfiguration}
 *
 * @author Maciej Walkowiak
 */
@Component
public class QueueNameObtainer {
	public String getQueueName(Class clazz) {
		return clazz.getName();
	}

	public String getQueueName(Request request) {
		return request.getClass().getName();
	}
}
