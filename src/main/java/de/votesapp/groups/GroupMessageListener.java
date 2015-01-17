package de.votesapp.groups;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Consumer;
import reactor.spring.annotation.Selector;
import de.votesapp.client.GroupMessage;
import de.votesapp.commands.CommandPlugin;
import de.votesapp.commands.plugins.Answer;

@Consumer
@Slf4j
public class GroupMessageListener {

	private final Reactor reactor;
	private final GroupService groupService;

	private final List<CommandPlugin> commandPlugins;
	private final CounterService counterService;

	@Autowired
	public GroupMessageListener(final List<CommandPlugin> commandPlugins, final GroupService groupService, final Reactor reactor,
			final CounterService counterService) {
		this.commandPlugins = commandPlugins;
		this.groupService = groupService;
		this.reactor = reactor;
		this.counterService = counterService;
	}

	@Selector(value = "group.inbox", reactor = "@rootReactor")
	// @Timed
	public void onGroupReceiveMessage(final Event<GroupMessage> evt) {
		final GroupMessage message = evt.getData();
		log.info("Process Message: {}", message);

		final Group group = groupService.createOrLoadGroup(message.getGroupId());
		group.addUserIfNotExists(message.sender());

		boolean answered = false;
		for (final CommandPlugin abstractCommandPlugin : commandPlugins) {
			answered = invokePlugin(message, group, answered, abstractCommandPlugin);
		}

		if (answered == false) {
			log.info("Found message we couldn't answer: {}", message);
			counterService.increment("plugins.unanswered");
		}

		groupService.save(group);
	}

	// @Timed
	private boolean invokePlugin(final GroupMessage message, final Group group, boolean answered, final CommandPlugin abstractCommandPlugin) {
		final Optional<Answer> answer = abstractCommandPlugin.interpret(message, group);

		if (answer.isPresent()) {
			counterService.increment("plugins.answers." + abstractCommandPlugin.getClass().getSimpleName());
			answered = true;
			try {
				answer.get().invoke(group, reactor, message);
			} catch (final Exception e) {
				log.error("The Plugin {} threw an exception on this message {}", abstractCommandPlugin.getClass().getName(), message);
			}
		}
		return answered;
	}
}
