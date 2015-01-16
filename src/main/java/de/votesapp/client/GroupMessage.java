package de.votesapp.client;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.votesapp.commands.TextNormalizer;

/**
 * Represents a Messages that got sent inside a group.
 */
// TODO: Lets see whether we should do this immutable. Pro: ..., Con: It should
// be neat and sweet., that would add boilerplate (like @JsonCreator).
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessage {

	public static GroupMessage of(final String groupId, final String text) {
		return new GroupMessage(null, escapeDot(groupId), null, null, text);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonCreator
	public static GroupMessage of( //
			@JsonProperty("_id") final String id, //
			@JsonProperty("_from") final String groupId, //
			@JsonProperty("participant") final String senderPhone, //
			@JsonProperty(value = "notify", required = false) final String senderName, //
			@JsonProperty("body") final String text) {

		return new GroupMessage(escapeDot(id), escapeDot(groupId), escapeDot(senderPhone), senderName, text);
	}

	private static String escapeDot(final String txt) {
		// TODO: That could be done by overwriting the mongo config like this:
		// @Override
		// public MappingMongoConverter mappingMongoConverter() throws Exception
		// {
		// final MappingMongoConverter mappingMongoConverter =
		// super.mappingMongoConverter();
		// // WhatsApp Keys are containing dots in domains.
		// mappingMongoConverter.setMapKeyDotReplacement("_");
		// return mappingMongoConverter;
		// }
		// But then we need to have one. Now it's autoconfigured
		return txt.replaceAll("\\.", "_");
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
	private String senderPhone;

	@Nullable
	private String senderName;

	private String text;

	public User sender() {
		// TODO: That should be done lazy
		return new User(senderPhone, senderName);
	}

	public String normalizedText() {
		return TextNormalizer.normalize(text);
	}
}
