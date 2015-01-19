package de.votesapp.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This makes it run on a developers pc without a connection to yowsup. Later we
 * could replace this by an more elegant ui.
 */
@RestController
@Profile("!yowsup")
public class WebClient implements WhatsAppClient {

	@Autowired
	private SimpMessagingTemplate brokerMessagingTemplate;

	private volatile List<GroupMessage> groupMessages = new ArrayList<>();

	@Override
	public void sendGroupMessage(final GroupMessage messageToSend) {
		brokerMessagingTemplate.convertAndSend("/receive", messageToSend);
	}

	@Override
	public GroupMessage[] fetchGroupMessages() {
		synchronized (groupMessages) {
			final GroupMessage[] messages = groupMessages.toArray(new GroupMessage[0]);
			groupMessages.clear();
			return messages;
		}
	}

	@RequestMapping("/send")
	public void sendPerRest(@RequestBody final SendModel sm) {
		synchronized (groupMessages) {
			final GroupMessage generatedMessage = new GroupMessage(UUID.randomUUID().toString(), sm.getGroup(), sm.getPhone(), null, sm.getText());
			groupMessages.add(generatedMessage);
		}
	}

	@Data
	public static class SendModel {
		private String phone;
		private String group;
		private String text;
	}
}
