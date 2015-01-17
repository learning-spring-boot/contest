package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {
	@InjectMocks
	private MessageService service;

	@Mock
	private MessageNotifier messageNotifier;

	@Mock
	private MessageRepository repository;

	private final SendMailRequest request = new SendMailRequest.Builder()
			.to("foo@bar.com")
			.subject("subject")
			.text("content")
			.build();

	@Test
	public void shouldChangeStatusAndSaveWhenSent() {
		// given
		Message message = Message.queued(request);

		// when
		service.messageSent(message);

		// then
		assertThat(message.getStatus()).isEqualTo(Message.Status.SENT);
		verify(repository).save(eq(message));
	}

	@Test
	public void shouldChangeStatusAndSaveWhenFailed() {
		// given
		Message message = Message.queued(request);
		String errorMessage = "some error";

		// when
		service.deliveryFailed(message, errorMessage);

		// then
		assertThat(message.getStatus()).isEqualTo(Message.Status.FAILED);
		assertThat(message.getErrorMessage()).isEqualTo(errorMessage);
		verify(repository).save(eq(message));
	}
}