package com.maciejwalkowiak.mercury.slack;

import com.maciejwalkowiak.mercury.core.Consumer;
import com.maciejwalkowiak.mercury.core.message.Message;
import com.maciejwalkowiak.mercury.core.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SlackConsumer implements Consumer<SlackRequest> {
	private static final Logger LOG = LoggerFactory.getLogger(SlackConsumer.class);

	private final SlackService slackService;
	private final MessageService messageService;

	@Autowired
	public SlackConsumer(SlackService slackService, MessageService messageService) {
		this.slackService = slackService;
		this.messageService = messageService;
	}

	@Override
	public void consume(Message<SlackRequest> message) {
		try {
			slackService.send(message.getRequest());
			messageService.messageSent(message);
		} catch (Exception e) {
			LOG.error("Sending Slack message failed", e);
			messageService.deliveryFailed(message, e.getMessage());
		}
	}
}
