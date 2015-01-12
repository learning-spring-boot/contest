package com.maciejwalkowiak.mercury.mail.javamail;

import com.maciejwalkowiak.mercury.mail.common.MailingService;
import com.maciejwalkowiak.mercury.mail.common.SendMailException;
import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
class JavaMailMailingService implements MailingService {
	private static final Logger LOG = LoggerFactory.getLogger(JavaMailMailingService.class);
	private final MailSender mailSender;

	@Autowired
	JavaMailMailingService(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void send(SendMailRequest sendMailRequest) throws SendMailException {
		LOG.info("Sending mail: {}", sendMailRequest);

		try {
			mailSender.send(toMailMessage(sendMailRequest));
		} catch (Exception e) {
			throw new SendMailException(e);
		}

	}

	SimpleMailMessage toMailMessage(SendMailRequest sendMailRequest) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(sendMailRequest.getTo());
		msg.setSubject(sendMailRequest.getSubject());
		msg.setText(sendMailRequest.getContent());

		return msg;
	}
}
