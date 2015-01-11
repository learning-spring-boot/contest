package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.Messenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
class MailController {
	private final Messenger messenger;

	@Autowired
	MailController(Messenger messenger) {
		this.messenger = messenger;
	}

	@RequestMapping(method = RequestMethod.POST)
	public MercuryMessage send(@RequestBody SendMailRequest sendMailRequest) {
		return messenger.publish(sendMailRequest);
	}
}
