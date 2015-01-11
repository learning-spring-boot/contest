package com.maciejwalkowiak.mercury.core;

import org.springframework.data.mongodb.repository.MongoRepository;

interface MercuryMessageRepository extends MongoRepository<MercuryMessage, String> {
}
