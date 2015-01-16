package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.core.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
class AsyncMessageNotifier implements MessageNotifier {
	private final Map<Class, Consumer> consumersMap = new HashMap<>();
	private final List<Consumer> consumers;

	@Autowired
	AsyncMessageNotifier(List<Consumer> consumers) {
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