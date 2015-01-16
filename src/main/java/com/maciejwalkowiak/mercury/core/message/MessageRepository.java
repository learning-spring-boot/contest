package com.maciejwalkowiak.mercury.core.message;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * MongoDB based repository for {@link Message}
 */
interface MessageRepository extends MongoRepository<Message, String> {
	@Query(count = true, value = "{ 'status' : ?0 }")
	long countByStatus(Message.Status status);
}
