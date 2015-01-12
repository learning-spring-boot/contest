package com.maciejwalkowiak.mercury.mail.javamail;

import com.maciejwalkowiak.mercury.mail.common.SendMailException;
import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JavaMailMailingServiceTest {
	@InjectMocks
	private JavaMailMailingService javaMailMailingService;
	@Mock
	private MailSender mailSender;

	private final SendMailRequest sendMailRequest = new SendMailRequest("foo@bar.com", "content", "subject");

	@Test
	public void convertsToJavaMailMessage() {
		// when
		SimpleMailMessage simpleMailMessage = javaMailMailingService.toMailMessage(sendMailRequest);

		// then
		areEqual(simpleMailMessage, sendMailRequest);
	}

	@Test
	public void shouldSendMessage() throws SendMailException {
		javaMailMailingService.send(sendMailRequest);

		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

		verify(mailSender).send(captor.capture());

		areEqual(captor.getValue(), sendMailRequest);
	}

	@Test(expected = SendMailException.class)
	public void shouldThrowExceptionOnError() throws SendMailException {
		doThrow(Exception.class).when(mailSender).send((SimpleMailMessage) any());

		javaMailMailingService.send(sendMailRequest);
	}

	private void areEqual(SimpleMailMessage mailMessage, SendMailRequest request) {
		assertThat(mailMessage.getTo()).contains(request.getTo());
		assertThat(mailMessage.getSubject()).isEqualTo(request.getSubject());
		assertThat(mailMessage.getText()).isEqualTo(request.getContent());
	}
}