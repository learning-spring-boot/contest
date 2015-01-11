package com.maciejwalkowiak.mercury.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api")
class ApiController {
	private final Optional<List<HateoasController>> controllers;

	@Autowired
	ApiController(Optional<List<HateoasController>> controllers) {
		this.controllers = controllers;
	}

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<ApiResource> links() {
		ApiResource apiResource = new ApiResource();
		apiResource.add(linkTo(ApiController.class).withSelfRel());

		if (controllers.isPresent()) {
			controllers.get().forEach(c ->
					apiResource.add(
							c.links().stream()
									.map(link -> new BracketsLink(link.getHref(), link.getRel()))
									.collect(Collectors.toList())
					)
			);
		}

		return new HttpEntity<>(apiResource);
	}
}
