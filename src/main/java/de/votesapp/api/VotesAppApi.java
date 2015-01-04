package de.votesapp.api;

import de.votesapp.model.NakedUser;
import de.votesapp.model.WhatsAppGroup;

public interface VotesAppApi {
	public void onGroupInvitation(final WhatsAppGroup whatsAppGroup);

	public void onGroupReceiveMessage(final GroupReceiveMessage message);

	public void onGroupJoinUser(final NakedUser nakedUser);

	public void onGroupLeaveUser(final NakedUser nakedUser);

	public void onGroupTitleChange(final GroupTitleChange groupTitleChange);
}
