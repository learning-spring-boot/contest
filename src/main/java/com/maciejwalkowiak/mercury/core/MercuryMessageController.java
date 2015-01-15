package com.maciejwalkowiak.mercury.core;

import com.fasterxml.jackson.annotation.JsonView;
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
class MercuryMessageController implements HateoasController {
	private final MercuryMessageRepository mercuryMessageRepository;

	@Autowired
	MercuryMessageController(MercuryMessageRepository mercuryMessageRepository) {
		this.mercuryMessageRepository = mercuryMessageRepository;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@JsonView(MercuryMessage.View.Summary.class)
	ResponseEntity<?> message(@PathVariable String id) {
		MercuryMessage message = mercuryMessageRepository.findOne(id);

		return message != null ? new ResponseEntity<>(message, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public List<Link> links() {
		return Arrays.asList(linkTo(methodOn(MercuryMessageController.class).message("{id}")).withRel("message"));
	}
}
