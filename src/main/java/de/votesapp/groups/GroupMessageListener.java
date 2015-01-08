package de.votesapp.groups;

import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Consumer;
import reactor.spring.annotation.Selector;
import de.votesapp.client.GroupMessage;
import de.votesapp.parser.Attitude;
import de.votesapp.parser.Command;
import de.votesapp.parser.HumanMessageParser;
import de.votesapp.parser.StatusRequest;

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
		final Optional<Command> command = parser.parse(message.getText());

		if (command.isPresent() == false) {
			log.debug("Found message we couldn't parse: ", message);
			return;
		}

		final Group group = groupService.createOrLoadGroup(message.getGroupId());

		// That could be so much nicer with dedicated classes and a base class
		// that has a method register()
		command.get().getAttitude().ifPresent(a -> registerAttitude(a, message, group));
		command.get().getAdditionals().ifPresent(a -> registerAdditionals(a, message, group));
		command.get().getStatusRequest().ifPresent(sr -> registerStatusRequest(sr, message, group));
	}

	private void registerAttitude(final Attitude attitude, final GroupMessage message, final Group group) {
		group.registerAttitude(message.getSender(), attitude);
		groupService.save(group);
	}

	private void registerAdditionals(final Integer additionals, final GroupMessage message, final Group group) {
		group.registerAdditionals(message.getSender(), additionals);
		groupService.save(group);
	}

	private void registerStatusRequest(final StatusRequest sr, final GroupMessage message, final Group group) {
		final StringBuilder statusMessage = new StringBuilder();

		final Map<Attitude, Integer> sumAttitudes = group.sumAttitudes();
		statusMessage.append("*thumb_up*: ").append(sumAttitudes.get(Attitude.POSITIVE)).append("\n");
		statusMessage.append("*thumb_down*: ").append(sumAttitudes.get(Attitude.NEGATIVE)).append("\n");
		statusMessage.append("*questionmark*: ").append(sumAttitudes.get(Attitude.UNKOWN)).append("\n");

		reactor.notify("group.outbox", Event.wrap(GroupMessage.messageOf(group.getGroupId(), statusMessage.toString())));
	}
}
