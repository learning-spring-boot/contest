package com.maciejwalkowiak.mercury.slack;

import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.Messenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.event.Event;
import reactor.function.Consumer;

@Component
class SlackConsumer implements Consumer<Event<MercuryMessage<SlackRequest>>> {
	private static final Logger LOG = LoggerFactory.getLogger(SlackConsumer.class);
	private final SlackService slackService;
	private final Messenger messenger;

	@Autowired
	public SlackConsumer(SlackService slackService, Messenger messenger) {
		this.slackService = slackService;
		this.messenger = messenger;
	}

	@Override
	public void accept(Event<MercuryMessage<SlackRequest>> event) {
		try {
			slackService.send(event.getData().getRequest());
			messenger.messageSent(event.getData());
		} catch (Exception e) {
			LOG.error("Sending Slack message failed", e);
			messenger.deliveryFailed(event.getData(), e.getMessage());
		}
	}
}
