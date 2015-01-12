package com.maciejwalkowiak.mercury.mail.sendgrid;

import com.maciejwalkowiak.mercury.mail.common.MailingService;
import com.maciejwalkowiak.mercury.mail.common.SendMailException;
import com.maciejwalkowiak.mercury.mail.common.SendMailRequest;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Primary
class SendGridMailingService implements MailingService {
	private static final Logger LOG = LoggerFactory.getLogger(SendGridMailingService.class);
	private final SendGrid sendGrid;

	@Autowired
	SendGridMailingService(SendGrid sendGrid) {
		this.sendGrid = sendGrid;
	}

	@Override
	public void send(SendMailRequest sendMailRequest) throws SendMailException {
		LOG.info("Sending mail: {}", sendMailRequest);

		try {
			SendGrid.Response response = sendGrid.send(toSendGridEmail(sendMailRequest));

			LOG.info("Response: {}, {}", response.getMessage(), response.getCode());

			if (response.getCode() != HttpStatus.OK.value()) {
				throw new SendMailException(response.getMessage());
			}
		} catch (SendGridException e) {
			throw new SendMailException(e);
		}
	}

	SendGrid.Email toSendGridEmail(SendMailRequest sendMailRequest) {
		return new SendGrid.Email()
				.setText(sendMailRequest.getContent())
				.setTo(new String[] { sendMailRequest.getTo() })
				.setSubject(sendMailRequest.getSubject());
	}
}
