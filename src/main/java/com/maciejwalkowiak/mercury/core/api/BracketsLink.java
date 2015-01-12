package com.maciejwalkowiak.mercury.core.api;

import org.springframework.hateoas.Link;

class BracketsLink extends Link {
	public BracketsLink(Link link) {
		super(link.getHref().replaceAll("%7B", "{").replaceAll("%7D", "}"), link.getRel());
	}
}
