package de.votesapp.api;

import de.votesapp.model.Message;
import de.votesapp.model.WhatsAppGroup;
import lombok.Data;

@Data
public class SendGroupMessage
{
    private WhatsAppGroup   whatsAppGroup;
    private Message message;
}
