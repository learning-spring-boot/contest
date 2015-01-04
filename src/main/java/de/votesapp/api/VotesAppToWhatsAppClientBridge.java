package de.votesapp.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.votesapp.client.GroupMessage;
import de.votesapp.client.WhatsAppClient;

/**
 * Connects the {@link WhatsAppClient} to the {@link VotesAppReceiveApi} and the
 * {@link VotesAppSendApi}
 *
 * Logic <-> VotesApp[Receive|Send]Api <-> Bridge <-> WhatsAppClient <-> Yowsup
 */
@Component
public class VotesAppToWhatsAppClientBridge implements VotesAppSendApi {

	private final WhatsAppClient whatsAppClient;
	private final VotesAppReceiveApi votesAppReceiveApi;

	@Autowired
	public VotesAppToWhatsAppClientBridge(final WhatsAppClient whatsAppClient, final VotesAppReceiveApi votesAppReceiveApi) {
		this.whatsAppClient = whatsAppClient;
		this.votesAppReceiveApi = votesAppReceiveApi;
	}

	@Scheduled(fixedDelay = 1000)
	public void pollMessages() {
		System.out.println("poll");
		// fetches until the "queue" is empty
		GroupMessage[] groupMessages;
		while ((groupMessages = whatsAppClient.fetchGroupMessages()).length != 0) {
			for (final GroupMessage groupMessage : groupMessages) {
				votesAppReceiveApi.onGroupReceiveMessage(groupMessage);
			}
		}
	}

	@Override
	public void sendGroupMessage(final String groupId, final String text) {
		whatsAppClient.sendGroupMessage(groupId, text);
	}
}
