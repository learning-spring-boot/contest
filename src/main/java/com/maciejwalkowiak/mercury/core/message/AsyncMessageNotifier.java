package com.maciejwalkowiak.mercury.core.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Notifies consumers about incoming message in asynchronous manner
 *
 * Has to be public because of @Async & JDK Proxies
 *
 * @author Maciej Walkowiak
 */
@Component
public class AsyncMessageNotifier implements MessageNotifier {
	private final Map<Class, MessageConsumer> consumersMap = new HashMap<>();
	private final List<MessageConsumer> consumers;

	@Autowired
	AsyncMessageNotifier(List<MessageConsumer> consumers) {
		this.consumers = consumers;
	}

	@PostConstruct
	public void initConsumersMap() {
		consumers.stream().forEach(c -> {
			Class persistentClass = (Class) ((ParameterizedType)c.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];

			consumersMap.put(persistentClass, c);
		});
	}

	@Override
	@Async
	public void notifyConsumers(Message<?> message) {
		Assert.notNull(message);

		if (consumersMap.containsKey(message.getRequest().getClass())) {
			consumersMap.get(message.getRequest().getClass()).consume(message);
		}
	}
}
