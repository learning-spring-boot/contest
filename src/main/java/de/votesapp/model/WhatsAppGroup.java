package de.votesapp.model;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class WhatsAppGroup
{
    private NakedUser      admin;
    private NakedUser      author;
    private LocalDateTime  created;

    private String         title;

    private Set<NakedUser> participants;
}
