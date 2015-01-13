package com.maciejwalkowiak.mercury.core.api;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * Exposes links to {@link com.maciejwalkowiak.mercury.core.api.ApiController}
 * so that they become visible under `/api` path
 *
 * @author Maciej Walkowiak
 */
public interface HateoasController {
	/**
	 * Gets HATEOAS links
	 *
	 * @return list of links or empty list
	 */
	List<Link> links();
}
