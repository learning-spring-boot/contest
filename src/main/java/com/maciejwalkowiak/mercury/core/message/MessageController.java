package com.maciejwalkowiak.mercury.core.message;

import com.maciejwalkowiak.mercury.core.api.HateoasController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Exposes message details through REST interface.
 *
 * Used to check message if message has been successfully saved.
 *
 * @author Maciej Walkowiak
 */
@RestController
@RequestMapping("/api/message/")
public class MessageController implements HateoasController {
	private final MessageRepository messageRepository;

	@Autowired
	MessageController(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> message(@PathVariable String id) {
		Message message = messageRepository.findOne(id);

		return message != null ? new ResponseEntity<>(message, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public List<Link> links() {
		return Arrays.asList(linkTo(methodOn(MessageController.class).message("{id}")).withRel("message"));
	}
}
