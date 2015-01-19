package de.votesapp.groups;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Selector;
import de.votesapp.client.GroupMessage;

@Service
public class Persistor {

	@Autowired
	private Reactor rootReactor;

	@Autowired
	private GroupMessageRepository groupMessageRepository;

	/*
	 * Temp. for debugging and replaying of messages.
	 */

	@Selector(value = "group.outbox", reactor = "@rootReactor")
	public void sendGroupMessage(final Event<GroupMessage> messageToSend) {
		final GroupMessage message = messageToSend.getData();
		groupMessageRepository.save(message);
	}

	@Selector(value = "group.inbox", reactor = "@rootReactor")
	public void receveGroupMessage(final Event<GroupMessage> messageToSend) {
		final GroupMessage message = messageToSend.getData();
		groupMessageRepository.save(message);
	}
}
