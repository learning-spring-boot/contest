package com.maciejwalkowiak.mercury.mail.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.Messenger;
import com.maciejwalkowiak.mercury.core.api.HateoasController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * API for receiving {@link com.maciejwalkowiak.mercury.mail.common.SendMailRequest} requests
 *
 * @author Maciej Walkowiak
 */
@RestController
@RequestMapping("/api/mail")
class MailController implements HateoasController {
	private final Messenger messenger;

	@Autowired
	MailController(Messenger messenger) {
		this.messenger = messenger;
	}

	@RequestMapping(method = RequestMethod.POST)
	@JsonView(MercuryMessage.View.Summary.class)
	public ResponseEntity<MercuryMessage> send(@RequestBody @Valid SendMailRequest sendMailRequest) {
		MercuryMessage message = messenger.publish(sendMailRequest);

		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@Override
	public List<Link> links() {
		return Arrays.asList(linkTo(MailController.class).withRel("mail"));
	}
}
