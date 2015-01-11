package com.maciejwalkowiak.mercury.core.api;

import org.springframework.hateoas.Link;

import java.util.List;

public interface HateoasController {
	List<Link> links();
}
