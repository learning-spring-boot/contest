package com.maciejwalkowiak.mercury.slack;

import com.fasterxml.jackson.annotation.JsonView;
import com.maciejwalkowiak.mercury.core.MercuryMessage;
import com.maciejwalkowiak.mercury.core.MercuryMessageController;
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
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/slack")
class SlackController implements HateoasController {
	private final Messenger messenger;

	@Autowired
	public SlackController(Messenger messenger) {
		this.messenger = messenger;
	}

	@RequestMapping(method = RequestMethod.POST)
	@JsonView(MercuryMessage.View.Summary.class)
	public ResponseEntity<Void> send(@RequestBody @Valid SlackRequest slackRequest) {
		MercuryMessage message = messenger.publish(slackRequest);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.location(linkTo(methodOn(MercuryMessageController.class).message(message.getId())).toUri())
				.build();
	}

	@Override
	public List<Link> links() {
		return Arrays.asList(linkTo(SlackController.class).withRel("slack"));
	}
}
