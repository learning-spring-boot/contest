package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessengerTest {
	@InjectMocks
	private Messenger messenger;
	@Mock
	private MessageService messageService;
	@Mock
	private MessageNotifier messageNotifier;

	private final SendMailRequest request = new SendMailRequest.Builder()
			.to("foo@bar.com")
			.subject("subject")
			.text("content")
			.build();

	@Test
	public void shouldSaveMessage() {
		messenger.publish(request);

		ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);

		verify(messageService).save(captor.capture());
		assertThat(captor.getValue().getRequest()).isEqualTo(request);
		assertThat(captor.getValue().getStatus()).isEqualTo(Message.Status.QUEUED);
	}

	@Test
	public void shouldPublishMessage() {
		// when
		messenger.publish(request);

		// then
		ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);

		verify(messageNotifier).notifyConsumers(captor.capture());

		Message message = captor.getValue();

		assertThat(message.getRequest()).isEqualTo(request);
	}
}