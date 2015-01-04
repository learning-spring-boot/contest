package de.votesapp.client;

public interface WhatsAppClient {

	public void sendGroupMessage(final String groupId, final String text);

	public GroupMessage[] fetchGroupMessages();
}
