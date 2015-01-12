package com.maciejwalkowiak.mercury.mail.common;

import com.maciejwalkowiak.mercury.core.QueueNameObtainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.Reactor;

import javax.annotation.PostConstruct;

import static reactor.event.selector.Selectors.$;

@Configuration
@ComponentScan
class MailConfiguration {

	@Autowired
	private MailConsumer mailConsumer;

	@Autowired
	private Reactor reactor;

	@Autowired
	private QueueNameObtainer queueNameObtainer;

	@PostConstruct
	void initReactor() {
		reactor.on($(queueNameObtainer.getQueueName(SendMailRequest.class)), mailConsumer);
	}
}
