package de.votesapp.api;

import de.votesapp.model.NakedUser;
import de.votesapp.model.WhatsAppGroup;

public interface OpenWhatsappApi
{
    public void onGroupInvitation(WhatsAppGroup whatsAppGroup);

    public void onGroupReceiveMessage(GroupReceiveMessage message);

    public void onGroupJoinUser(NakedUser nakedUser);

    public void onGroupLeaveUser(NakedUser nakedUser);

    public void onGroupTitleChange(GroupTitleChange groupTitleChange);
}
