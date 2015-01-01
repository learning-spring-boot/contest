package de.votesapp.model;

import lombok.Data;

@Data
public class Message
{
    private NakedUser author;
    private String    text;
}
