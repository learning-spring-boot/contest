package com.maciejwalkowiak.mercury.core.api;

import org.springframework.hateoas.Link;

class BracketsLink extends Link {
	public BracketsLink(String href, String rel) {
		super(href.replaceAll("%7B", "{").replaceAll("%7D", "}"), rel);
	}
}
