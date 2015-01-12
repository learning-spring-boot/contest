package com.maciejwalkowiak.mercury.core;

import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.core.Reactor;
import reactor.event.Event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessengerImplTest {
	@InjectMocks
	private MessengerImpl messenger;
	@Mock
	private MercuryMessageRepository repository;
	@Mock
	private QueueNameObtainer queueNameObtainer;
	@Mock
	private Reactor rootReactor;

	private final SendMailRequest request = new SendMailRequest("foo@bar.com", "content", "subject");

	@Test
	public void shouldSaveMessage() {
		messenger.publish(request);

		ArgumentCaptor<MercuryMessage> captor = ArgumentCaptor.forClass(MercuryMessage.class);

		verify(repository).save(captor.capture());
		assertThat(captor.getValue().getRequest()).isEqualTo(request);
		assertThat(captor.getValue().getStatus()).isEqualTo(MercuryMessage.Status.QUEUED);
	}

	@Test
	public void shouldPublishMessage() {
		// given
		when(queueNameObtainer.getQueueName(eq(request))).thenReturn("queue");

		// when
		messenger.publish(request);

		// then
		ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);

		verify(rootReactor).notify(eq("queue"), captor.capture());

		MercuryMessage message = (MercuryMessage) captor.getValue().getData();

		assertThat(message.getRequest()).isEqualTo(request);
	}

	@Test
	public void shouldChangeStatusAndSaveWhenSent() {
		// given
		MercuryMessage message = MercuryMessage.queued(request);

		// when
		messenger.messageSent(message);

		// then
		assertThat(message.getStatus()).isEqualTo(MercuryMessage.Status.SENT);
		verify(repository).save(eq(message));
	}

	@Test
	public void shouldChangeStatusAndSaveWhenFailed() {
		// given
		MercuryMessage message = MercuryMessage.queued(request);
		String errorMessage = "some error";

		// when
		messenger.deliveryFailed(message, errorMessage);

		// then
		assertThat(message.getStatus()).isEqualTo(MercuryMessage.Status.FAILED);
		assertThat(message.getErrorMessage()).isEqualTo(errorMessage);
		verify(repository).save(eq(message));
	}
}