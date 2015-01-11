package com.maciejwalkowiak.mercury.core;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
class MercuryMessageController {
	private final MercuryMessageRepository mercuryMessageRepository;

	@Autowired
	MercuryMessageController(MercuryMessageRepository mercuryMessageRepository) {
		this.mercuryMessageRepository = mercuryMessageRepository;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@JsonView(MercuryMessage.View.Summary.class)
	MercuryMessage message(@PathVariable String id) {
		return mercuryMessageRepository.findOne(id);
	}
}