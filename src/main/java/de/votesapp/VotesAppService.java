package de.votesapp;

import java.util.Optional;

import org.springframework.stereotype.Service;

import de.votesapp.api.GroupReceiveMessage;
import de.votesapp.api.GroupTitleChange;
import de.votesapp.api.OpenWhatAppSender;
import de.votesapp.api.OpenWhatsappApi;
import de.votesapp.model.Attends;
import de.votesapp.model.NakedUser;
import de.votesapp.model.WhatsAppGroup;
import de.votesapp.model.VotesAppGroup;

@Service
public class VotesAppService implements OpenWhatsappApi
{
    private GroupRepository    groupRepository;

    private HumanMessageParser parser;

    private OpenWhatAppSender  sender;

    @Override
    public void onGroupInvitation(WhatsAppGroup whatsAppGroup)
    {
        groupRepository.save(whatsAppGroup);
    }

    @Override
    public void onGroupReceiveMessage(GroupReceiveMessage message)
    {
        // WhatsAppGroup zu WoodleGroup
        VotesAppGroup votesAppGroup = groupRepository.findByGroup(message.getWhatsAppGroup());

        // Parse text
        Optional<Attends> attends = parser.parse(message.getMessage().getText());

        attends.ifPresent(a -> votesAppGroup.setAttends(message, a));
    }

    @Override
    public void onGroupJoinUser(NakedUser nakedUser)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGroupLeaveUser(NakedUser nakedUser)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGroupTitleChange(GroupTitleChange groupTitleChange)
    {
        // TODO Auto-generated method stub

    }

}
