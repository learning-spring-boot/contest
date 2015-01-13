package com.maciejwalkowiak.mercury.core;

import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import com.maciejwalkowiak.mercury.mail.common.SendMailRequestBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueueNameObtainerTest {
	final String QUEUE_NAME = "com.maciejwalkowiak.mercury.mail.common.SendMailRequest";
	private QueueNameObtainer queueNameObtainer = new QueueNameObtainer();

	@Test
	public void testQueueNameForClass() {
		assertThat(queueNameObtainer.getQueueName(SendMailRequest.class)).isEqualTo(QUEUE_NAME);
	}

	@Test
	public void testQueueNameForInstance() {
		SendMailRequest sendMailRequest = new SendMailRequestBuilder()
				.to("foo@bar.com")
				.subject("subject")
				.text("content")
				.build();
		assertThat(queueNameObtainer.getQueueName(sendMailRequest)).isEqualTo(QUEUE_NAME);
	}

}