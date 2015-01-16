package com.maciejwalkowiak.mercury.core.message;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB based repository for {@link Message}
 */
interface MessageRepository extends MongoRepository<Message, String> {
}
