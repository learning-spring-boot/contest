package de.votesapp.client;

import static org.apache.commons.lang3.StringUtils.trim;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
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
		message.put("to", GroupMessage.unescapeDot(messageToSend.getGroupId()));
		// TODO: Add test that the text is trimmed. WhatsApp doesn't accept
		// messages ending with \n!
		message.put("text", trim(messageToSend.getText()));

		restTemplate.postForEntity(yowsupRestConfig.getBaseUrl() + "/messages/outbox", message, Void.class);
	}

	/**
	 * Receive all new messages and delete them from the inbox.
	 */
	@Override
	public GroupMessage[] fetchGroupMessages() throws WhatsAppConnectionException {
		try {
			final GroupMessage[] messages = restTemplate.getForObject(yowsupRestConfig.getBaseUrl() + "/messages/inbox", GroupMessage[].class);

			deleteMessages(messages);

			return messages;
		} catch (final HttpClientErrorException e) {
			if (e.getStatusCode().value() == 401) {
				throw new WhatsAppConnectionException(
						"Wrong credentials for yowsup restservice are given. The endpoint returned 401. Cfg: " + yowsupRestConfig, e);
			} else {
				throw new WhatsAppConnectionException( //
						MessageFormat.format("Can''t access Yowsup Service. Error: {0} {1} - {2}", //
								e.getStatusCode().value(), //
								e.getStatusCode().getReasonPhrase(), //
								e.getStatusText()), //
								e);
			}
		} catch (final ResourceAccessException e) {
			throw new WhatsAppConnectionException("Seems the VotesApp-Yowsup Rest Serviers isn't running. Is the Config correct? " + yowsupRestConfig, e);
		}
	}

	private void deleteMessages(final GroupMessage... messages) {
		for (final GroupMessage msg : messages) {
			restTemplate.delete(yowsupRestConfig.getBaseUrl() + "/messages/inbox/{id}", GroupMessage.unescapeDot(msg.getId()));
		}
	}
}
