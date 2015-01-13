package com.maciejwalkowiak.mercury.mail.common;

import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.Messenger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.event.Event;

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
	private Messenger messenger;

	@Test
	public void shouldFailWhenMailingServiceNotPresent() {
		mailConsumer = new MailConsumer(Optional.empty(), messenger);

		MercuryMessage<SendMailRequest> message = mock(MercuryMessage.class);

		mailConsumer.accept(new Event<>(message));

		verify(messenger).deliveryFailed(eq(message), anyString());
	}

	@Test
	public void shouldSendMessage() throws SendMailException {
		mailConsumer = new MailConsumer(Optional.of(mailingService), messenger);

		MercuryMessage<SendMailRequest> message = mock(MercuryMessage.class);

		mailConsumer.accept(new Event<>(message));

		verify(mailingService).send(eq(message.getRequest()));
		verify(messenger).messageSent(eq(message));
	}

	@Test
	public void shouldFailWhenMailingServiceFailed() throws SendMailException {
		mailConsumer = new MailConsumer(Optional.of(mailingService), messenger);

		MercuryMessage<SendMailRequest> message = mock(MercuryMessage.class);
		doThrow(new SendMailException("some message")).when(mailingService).send(any());

		mailConsumer.accept(new Event<>(message));

		verify(messenger).deliveryFailed(eq(message), eq("some message"));
	}



}