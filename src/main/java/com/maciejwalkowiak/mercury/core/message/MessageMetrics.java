package com.maciejwalkowiak.mercury.core.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Exposes metrics for number of messages by status
 *
 * @author Maciej Walkowiak
 */
@Component
class MessageMetrics implements PublicMetrics {
	private final MessageRepository messageRepository;

	@Autowired
	MessageMetrics(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@Override
	public Collection<Metric<?>> metrics() {
		return Arrays.stream(Message.Status.values())
				.map(s -> new Metric<>("message." + s.name().toLowerCase(), messageRepository.countByStatus(s)))
				.collect(Collectors.toList());
	}
}
