package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.QueueNameObtainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.Reactor;

import javax.annotation.PostConstruct;

import java.util.Queue;

import static reactor.event.selector.Selectors.$;

@Configuration
@ConditionalOnProperty(name = "spring.mail.host")
@ComponentScan
class MailConfiguration {
	@Autowired
	private Reactor reactor;

	@Autowired
	private MailConsumer mailConsumer;

	@Autowired
	private QueueNameObtainer queueNameObtainer;

	@PostConstruct
	void initReactor() {
		reactor.on($(queueNameObtainer.getQueueName(SendMailRequest.class)), mailConsumer);
	}
}
