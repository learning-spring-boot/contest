package de.votesapp.groups;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Consumer;
import reactor.spring.annotation.Selector;
import de.votesapp.client.GroupMessage;
import de.votesapp.parser.Command;
import de.votesapp.parser.HumanMessageParser;

@Consumer
@Slf4j
public class GroupMessageListener {

	private final HumanMessageParser parser;

	private final GroupService groupService;

	private final Reactor reactor;

	@Autowired
	public GroupMessageListener(final HumanMessageParser parser, final GroupService groupService, final Reactor reactor) {
		this.parser = parser;
		this.groupService = groupService;
		this.reactor = reactor;
	}

	@Selector(value = "group.inbox", reactor = "@rootReactor")
	public void onGroupReceiveMessage(final Event<GroupMessage> evt) {
		final GroupMessage message = evt.getData();
		log.info("Process Message: {}", message);

		final Group group = groupService.createOrLoadGroup(message.getGroupId());
		group.addUserIfNotExists(message.getSender());

		final Optional<Command> command = parser.parse(message.getText());
		if (command.isPresent()) {
			command.get().execute(message, group, reactor);
			groupService.save(group);
		} else {
			log.debug("Found message we couldn't parse: ", message);
		}
		groupService.save(group);
	}
}
