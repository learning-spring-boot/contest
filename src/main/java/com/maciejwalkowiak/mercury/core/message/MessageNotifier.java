package com.maciejwalkowiak.mercury.core.message;

import org.springframework.scheduling.annotation.Async;

public interface MessageNotifier {
	@Async
	void notifyConsumers(Message<?> message);
}
