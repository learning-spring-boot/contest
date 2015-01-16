package com.maciejwalkowiak.mercury.mail.common;

import com.maciejwalkowiak.mercury.core.message.Message;
import com.maciejwalkowiak.mercury.core.message.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MailConsumerTest {
	private MailConsumer mailConsumer;

	@Mock
	private MailingService mailingService;

	@Mock
	private MessageService messageService;

	@Test
	public void shouldFailWhenMailingServiceNotPresent() {
		mailConsumer = new MailConsumer(Optional.empty(), messageService);

		Message<SendMailRequest> message = mock(Message.class);

		mailConsumer.consume(message);

		verify(messageService).deliveryFailed(eq(message), anyString());
	}

	@Test
	public void shouldSendMessage() throws SendMailException {
		mailConsumer = new MailConsumer(Optional.of(mailingService), messageService);

		Message<SendMailRequest> message = mock(Message.class);

		mailConsumer.consume(message);

		verify(mailingService).send(eq(message.getRequest()));
		verify(messageService).messageSent(eq(message));
	}

	@Test
	public void shouldFailWhenMailingServiceFailed() throws SendMailException {
		mailConsumer = new MailConsumer(Optional.of(mailingService), messageService);

		Message<SendMailRequest> message = mock(Message.class);
		doThrow(new SendMailException("some message")).when(mailingService).send(any());

		mailConsumer.consume(message);

		verify(messageService).deliveryFailed(eq(message), eq("some message"));
	}



}