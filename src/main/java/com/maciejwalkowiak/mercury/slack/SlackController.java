package com.maciejwalkowiak.mercury.slack;

import com.maciejwalkowiak.mercury.core.api.HateoasController;
import com.maciejwalkowiak.mercury.core.message.Message;
import com.maciejwalkowiak.mercury.core.message.MessageController;
import com.maciejwalkowiak.mercury.core.message.Messenger;
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
	public ResponseEntity<Void> send(@RequestBody @Valid SlackRequest slackRequest) {
		Message message = messenger.publish(slackRequest);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.location(linkTo(methodOn(MessageController.class).message(message.getId())).toUri())
				.build();
	}

	@Override
	public List<Link> links() {
		return Arrays.asList(linkTo(SlackController.class).withRel("slack"));
	}
}
