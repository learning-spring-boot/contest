package com.maciejwalkowiak.mercury.core;

import org.springframework.scheduling.annotation.Async;

public interface MessagePublisher {
	@Async
	void notifyConsumers(MercuryMessage<?> message);
}
