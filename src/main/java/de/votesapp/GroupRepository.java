package de.votesapp;

import de.votesapp.model.WhatsAppGroup;
import de.votesapp.model.VotesAppGroup;

public interface GroupRepository
{
    // mongodb repository

    void save(WhatsAppGroup whatsAppGroup);

    VotesAppGroup findByGroup(WhatsAppGroup whatsAppGroup);
}
