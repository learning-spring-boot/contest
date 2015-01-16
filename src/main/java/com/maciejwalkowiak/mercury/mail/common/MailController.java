package com.maciejwalkowiak.mercury.mail.common;

import com.fasterxml.jackson.annotation.JsonView;
import com.maciejwalkowiak.mercury.core.message.Message;
import com.maciejwalkowiak.mercury.core.message.MessageController;
import com.maciejwalkowiak.mercury.core.message.Messenger;
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
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
	public ResponseEntity<Void> send(@RequestBody @Valid SendMailRequest sendMailRequest) {
		Message message = messenger.publish(sendMailRequest);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.location(linkTo(methodOn(MessageController.class).message(message.getId())).toUri())
				.build();
	}

	@Override
	public List<Link> links() {
		return Arrays.asList(linkTo(MailController.class).withRel("mail"));
	}
}
