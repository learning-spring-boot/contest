package de.votesapp.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final String baseUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public YowsupRestClient(@Value("${yowsup.baseUrl}") String baseUrl,
	    RestTemplate restTemplate) {
	this.baseUrl = baseUrl;
	this.restTemplate = restTemplate;
    }

    @Override
    public void sendGroupMessage(String groupId, String text) {
	GroupMessage messageToSend = GroupMessage.ownMessageOf(groupId, text);

	// WhatsApp won't return the unique messages IDs, so we don't receive a
	// resource ID for that.
	restTemplate.postForEntity(baseUrl + "/groupMessage", messageToSend,
		Void.class);
    }

    /**
     * Receive all new messages and delete them from the inbox.
     */
    @Override
    public GroupMessage[] fetchGroupMessages() {
	GroupMessage[] messages = restTemplate.getForObject(baseUrl
		+ "/groupMessage", GroupMessage[].class);

	deleteMessages(messages);

	return messages;
    }

    private void deleteMessages(GroupMessage... messages) {
	for (GroupMessage msg : messages) {
	    restTemplate.delete(baseUrl + "/groupMessage/{id}", msg.getId());
	}
    }

}