package de.votesapp.model;

import java.util.Map;

import de.votesapp.api.GroupReceiveMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VotesAppGroup extends WhatsAppGroup
{
    private Map<NakedUser, Attends> userStatus;

    public void setAttends(GroupReceiveMessage message, Attends a)
    {
        NakedUser author = message.getMessage().getAuthor();
        userStatus.put(author, a);
    }
}
