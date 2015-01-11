package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.Messenger;
import com.maciejwalkowiak.mercury.core.api.HateoasController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/mail")
class MailController implements HateoasController {
	private final Messenger messenger;

	@Autowired
	MailController(Messenger messenger) {
		this.messenger = messenger;
	}

	@RequestMapping(method = RequestMethod.POST)
	public MercuryMessage send(@RequestBody SendMailRequest sendMailRequest) {
		return messenger.publish(sendMailRequest);
	}

	@Override
	public List<Link> links() {
		return Arrays.asList(linkTo(MailController.class).withRel("mail"));
	}
}
