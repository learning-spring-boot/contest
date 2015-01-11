package de.votesapp.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client to the Yowsup-Rest API
 *
 * Currently only sending and
 */
@Component
@Profile("yowsup")
public class YowsupRestClient implements WhatsAppClient {
	private final RestTemplate restTemplate;
	private final YowsupRestConfig yowsupRestConfig;

	@Autowired
	public YowsupRestClient(final YowsupRestConfig yowsupRestConfig, final RestTemplate restTemplate) {
		this.yowsupRestConfig = yowsupRestConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public void sendGroupMessage(final GroupMessage messageToSend) {
		// WhatsApp won't return the unique messages IDs, so we don't receive a
		// resource ID for that.
		final Map<String, String> message = new HashMap<>();
		message.put("to", messageToSend.getGroupId());
		message.put("text", messageToSend.getText());

		restTemplate.postForEntity(yowsupRestConfig.getBaseUrl() + "/messages/outbox", message, Void.class);
	}

	/**
	 * Receive all new messages and delete them from the inbox.
	 */
	@Override
	public GroupMessage[] fetchGroupMessages() {
		final GroupMessage[] messages = restTemplate.getForObject(yowsupRestConfig.getBaseUrl() + "/messages/inbox", GroupMessage[].class);

		deleteMessages(messages);

		return messages;
	}

	private void deleteMessages(final GroupMessage... messages) {
		for (final GroupMessage msg : messages) {
			restTemplate.delete(yowsupRestConfig.getBaseUrl() + "/messages/inbox/{id}", msg.getId());
		}
	}

}
