package com.maciejwalkowiak.mercury.core;

import com.maciejwalkowiak.mercury.core.message.Message;

public interface Consumer<T extends Request> {
	void consume(Message<T> message);
}
