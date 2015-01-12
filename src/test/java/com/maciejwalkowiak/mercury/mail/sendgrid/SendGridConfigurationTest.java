package com.maciejwalkowiak.mercury.mail.sendgrid;

import com.maciejwalkowiak.mercury.MercuryApplication;
import com.maciejwalkowiak.mercury.mail.common.MailingService;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SendGridConfigurationTest {

	private AnnotationConfigApplicationContext context;

	@Test
	public void sendGridMailSenderIsCreatedOnProperty() {
		this.context = new AnnotationConfigApplicationContext();
		EnvironmentTestUtils.addEnvironment(context, "sendgrid.username");
		this.context.register(MercuryApplication.class);
		this.context.refresh();

		assertThat(context.getBean(SendGridMailingService.class)).isNotNull();
	}

	@Test(expected = NoSuchBeanDefinitionException.class)
	public void sendGridMailSenderIsNotDefinedByDefault() {
		this.context = new AnnotationConfigApplicationContext();
		this.context.register(MercuryApplication.class);
		this.context.refresh();

		context.getBean(SendGridMailingService.class);
	}

	@Test
	public void sendGridMailSenderIsDefinedWhenBothProvidersAreDefined() {
		this.context = new AnnotationConfigApplicationContext();
		EnvironmentTestUtils.addEnvironment(context, "sendgrid.username");
		EnvironmentTestUtils.addEnvironment(context, "spring.mail.host");
		this.context.register(MercuryApplication.class);
		this.context.refresh();

		assertThat(context.getBean(MailingService.class)).isNotNull().isInstanceOf(SendGridMailingService.class);
	}

}