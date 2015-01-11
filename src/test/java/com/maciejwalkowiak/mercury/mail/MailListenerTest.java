package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.Messenger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSender;

import static org.mockito.Mockito.mock;

public class MailListenerTest {
	private MailListener mailListener;

	@Before
	public void setUp() {
		mailListener = new MailListener(mock(MailSender.class), mock(Messenger.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_for_null_message() {
		mailListener.onApplicationEvent(null);
	}
}