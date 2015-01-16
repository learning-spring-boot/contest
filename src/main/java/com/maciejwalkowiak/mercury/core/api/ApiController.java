package com.maciejwalkowiak.mercury.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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

	/**
	 * Spring HATEOAS performs URL encoding and replaces characters "{" and "}" that are useful to show templated URL.
	 *
	 * BracketsLink is a hacky class that takes {@link org.springframework.hateoas.Link}
	 * from Spring HATEOAS package and brings back brackets "{" and "}"
	 *
	 * @author Maciej Walkowiak
	 */
	private static class BracketsLink extends Link {
		public BracketsLink(Link link) {
			super(link.getHref().replaceAll("%7B", "{").replaceAll("%7D", "}"), link.getRel());
		}
	}
}