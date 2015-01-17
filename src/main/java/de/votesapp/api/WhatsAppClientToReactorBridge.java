package de.votesapp.api;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.scheduling.annotation.Scheduled;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.spring.annotation.Consumer;
import reactor.spring.annotation.Selector;
import de.votesapp.client.GroupMessage;
import de.votesapp.client.WhatsAppClient;

/**
 * Connects the {@link WhatsAppClient} to the {@link VotesAppSendApi} and
 * published received {@link GroupMessage}s via Reactor to the Application <br/>
 * Logic <-> Reactor <-> Bridge <-> WhatsAppClient <-> Yowsup
 */
@Slf4j
@Consumer
public class WhatsAppClientToReactorBridge {

	private final WhatsAppClient whatsAppClient;
	private final Reactor rootReactor;
	private final CounterService counterService;

	@Autowired
	public WhatsAppClientToReactorBridge(final WhatsAppClient whatsAppClient, final Reactor rootReactor, final CounterService counterService) {
		this.whatsAppClient = whatsAppClient;
		this.rootReactor = rootReactor;
		this.counterService = counterService;
	}

	/**
	 * Poll Messages and translate into Reactor Events
	 */
	// Replace by WebSockets
	@Scheduled(fixedDelay = 1000)
	public void pollMessages() {
		// fetches until the "queue" is empty
		GroupMessage[] groupMessages;
		while ((groupMessages = whatsAppClient.fetchGroupMessages()).length != 0) {
			for (final GroupMessage groupMessage : groupMessages) {
				absorpGroupMessage(groupMessage);
			}
		}
	}

	private void absorpGroupMessage(final GroupMessage groupMessage) {
		counterService.increment("group.inbox");
		rootReactor.notify("group.inbox", Event.wrap(groupMessage));
	}

	@Selector(value = "group.outbox", reactor = "@rootReactor")
	public void sendGroupMessage(final Event<GroupMessage> messageToSend) {
		counterService.increment("group.outbox");
		// TODO: Avoid NPE here
		final GroupMessage message = messageToSend.getData();
		log.info("Send Message: {}", message);
		whatsAppClient.sendGroupMessage(message);
	}
}
