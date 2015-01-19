package de.votesapp.groups;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.votesapp.client.GroupMessage;

public interface GroupMessageRepository extends MongoRepository<GroupMessage, String> {

}
