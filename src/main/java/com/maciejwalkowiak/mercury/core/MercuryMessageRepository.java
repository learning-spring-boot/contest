package com.maciejwalkowiak.mercury.core;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB based repository for {@link com.maciejwalkowiak.mercury.core.MercuryMessage}
 */
interface MercuryMessageRepository extends MongoRepository<MercuryMessage, String> {
}
