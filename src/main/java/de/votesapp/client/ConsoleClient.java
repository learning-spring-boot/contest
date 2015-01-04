package de.votesapp.client;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Profile;

/**
 * This makes it run on a developers pc without a connection to yowsup.
 */
@Slf4j
@Profile("!yowsup")
public class ConsoleClient implements WhatsAppClient {

	@Override
	public void sendGroupMessage(final String groupId, final String text) {
		log.info("Sent Message: group {}, text {}");
	}

	@Override
	public GroupMessage[] fetchGroupMessages() {
		return new GroupMessage[0];
	}
}
