package com.maciejwalkowiak.mercury.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * In Mercury API is dynamic and depending how application is configured some URLs are available and some are not.
 * {@link com.maciejwalkowiak.mercury.core.api.ApiController} exposes an array of all available URLs with current configuration
 *
 * @author Maciej Walkowiak
 */
@Controller
@RequestMapping(value = "/api")
class ApiController {
	private final Optional<List<HateoasController>> controllers;

	@Autowired
	ApiController(Optional<List<HateoasController>> controllers) {
		this.controllers = controllers;
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.OPTIONS })
	public HttpEntity<ApiResource> links() {
		ApiResource apiResource = new ApiResource();
		apiResource.add(linkTo(ApiController.class).withSelfRel());

		if (controllers.isPresent()) {
			controllers.get().forEach(c ->
					apiResource.add(
							c.links().stream()
									.map(link -> new BracketsLink(link))
									.collect(Collectors.toList())
					)
			);
		}

		return new HttpEntity<>(apiResource);
	}

	private static class ApiResource extends ResourceSupport {
	}
}