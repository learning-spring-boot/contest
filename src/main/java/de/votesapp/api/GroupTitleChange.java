package de.votesapp.api;

import de.votesapp.model.WhatsAppGroup;
import lombok.Data;

@Data
public class GroupTitleChange
{
    private WhatsAppGroup  whatsAppGroup;
    private String title;
}
