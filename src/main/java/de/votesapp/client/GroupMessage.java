package de.votesapp.client;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Messages that got sent inside a group.
 */
// TODO: Lets see whether we should do this immutable. Pro: ..., Con: It should
// be neat and sweet., that would add boilerplate (like @JsonCreator).
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessage {

	public static GroupMessage messageOf(final String groupId, final String text) {
		return new GroupMessage(null, groupId, null, text);
	}

	/**
	 * Unique messageId. 4917917413454-1, 2, 3, ...
	 *
	 * For messages we sent this can be <tt>null</tt>.
	 */
	@Nullable
	private String id;

	/**
	 * Unique Identifier of the group like: 491797529324-12812497
	 */
	private String groupId;

	/**
	 * Phone Number of the Sender like: 491791287124
	 *
	 * For messages we sent this can be <tt>null</tt>.
	 */
	@Nullable
	private String sender;

	private String text;
}
