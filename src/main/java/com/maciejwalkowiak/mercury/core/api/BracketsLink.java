package com.maciejwalkowiak.mercury.core.api;

import org.springframework.hateoas.Link;

/**
 * Spring HATEOAS performs URL encoding and replaces characters "{" and "}" that are useful to show templated URL.
 *
 * {@link com.maciejwalkowiak.mercury.core.api.BracketsLink} is a hacky class that takes {@link org.springframework.hateoas.Link}
 * from Spring HATEOAS package and brings back brackets "{" and "}"
 *
 * @author Maciej Walkowiak
 */
class BracketsLink extends Link {
	public BracketsLink(Link link) {
		super(link.getHref().replaceAll("%7B", "{").replaceAll("%7D", "}"), link.getRel());
	}
}
