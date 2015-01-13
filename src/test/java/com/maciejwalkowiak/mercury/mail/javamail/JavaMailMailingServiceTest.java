package com.maciejwalkowiak.mercury.mail.javamail;

import com.maciejwalkowiak.mercury.mail.common.SendMailException;
import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import com.maciejwalkowiak.mercury.mail.common.SendMailRequestBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JavaMailMailingServiceTest {
	@InjectMocks
	private JavaMailMailingService javaMailMailingService;
	@Mock
	private MailSender mailSender;

	private final SendMailRequest request = new SendMailRequestBuilder()
			.to("foo@bar.com")
			.subject("subject")
			.text("content")
			.build();

	@Test
	public void convertsToJavaMailMessage() {
		// when
		SimpleMailMessage simpleMailMessage = javaMailMailingService.toMailMessage(request);

		// then
		areEqual(simpleMailMessage, request);
	}

	@Test
	public void shouldSendMessage() throws SendMailException {
		javaMailMailingService.send(request);

		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

		verify(mailSender).send(captor.capture());

		areEqual(captor.getValue(), request);
	}

	@Test(expected = SendMailException.class)
	public void shouldThrowExceptionOnError() throws SendMailException {
		doThrow(Exception.class).when(mailSender).send((SimpleMailMessage) any());

		javaMailMailingService.send(request);
	}

	private void areEqual(SimpleMailMessage mailMessage, SendMailRequest request) {
		assertThat(mailMessage.getTo()).containsAll(request.getTo());
		assertThat(mailMessage.getCc()).containsAll(request.getCc());
		assertThat(mailMessage.getBcc()).containsAll(request.getBcc());
		assertThat(mailMessage.getSubject()).isEqualTo(request.getSubject());
		assertThat(mailMessage.getText()).isEqualTo(request.getText());
	}
}