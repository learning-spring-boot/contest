package com.maciejwalkowiak.mercury.core;

import org.springframework.stereotype.Component;

@Component
public class QueueNameObtainer {
	public String getQueueName(Class clazz) {
		return clazz.getName();
	}

	public String getQueueName(Request request) {
		return request.getClass().getName();
	}
}
