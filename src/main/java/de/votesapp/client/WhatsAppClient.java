package de.votesapp.client;

public interface WhatsAppClient {

    public void sendGroupMessage(String groupId, String text);

    public GroupMessage[] fetchGroupMessages();
}