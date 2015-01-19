package de.votesapp.client;

public interface WhatsAppClient {

	public void sendGroupMessage(final GroupMessage messageToSend);

	public GroupMessage[] fetchGroupMessages() throws WhatsAppConnectionException;
}
