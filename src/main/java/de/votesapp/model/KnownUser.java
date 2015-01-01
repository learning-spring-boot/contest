package de.votesapp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KnownUser extends NakedUser
{
    private String name;
}
