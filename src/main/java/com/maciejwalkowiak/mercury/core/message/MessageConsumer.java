package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.core.Request;
import com.maciejwalkowiak.mercury.core.message.Message;

/**
 * @author Maciej Walkowiak
 */
public interface MessageConsumer<T extends Request> {
	void consume(Message<T> message);
}
