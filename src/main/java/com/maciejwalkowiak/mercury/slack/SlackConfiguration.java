package com.maciejwalkowiak.mercury.slack;

import com.maciejwalkowiak.mercury.core.QueueNameObtainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.Reactor;

import javax.annotation.PostConstruct;

import static reactor.event.selector.Selectors.$;

@Configuration
@ComponentScan
@ConditionalOnProperty(name = "slack.hook.url")
class SlackConfiguration {
	@Autowired
	private SlackConsumer slackConsumer;

	@Autowired
	private Reactor reactor;

	@Autowired
	private QueueNameObtainer queueNameObtainer;

	@PostConstruct
	void initReactor() {
		reactor.on($(queueNameObtainer.getQueueName(SlackRequest.class)), slackConsumer);
	}
}
