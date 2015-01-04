package de.votesapp.api;

import de.votesapp.client.GroupMessage;

public interface VotesAppReceiveApi {
	public void onGroupReceiveMessage(final GroupMessage message);
}
