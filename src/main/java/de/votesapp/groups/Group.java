package de.votesapp.groups;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.springframework.data.annotation.Id;

import de.votesapp.client.User;
import de.votesapp.commands.plugins.Attitude;

@Data
public class Group {
	@Id
	private final String groupId;

	private String question;

	private Date votingSince = new Date();

	private final Map<String, User> users = new HashMap<>();

	private final Map<String, Attitude> userAttitude = new HashMap<>();

	private final Map<String, Integer> userAdditionals = new HashMap<>();

	public Group(final String groupId) {
		// TODO: Replace that by mongomapper.
		this.groupId = groupId.replaceAll("\\.", "_");
	}

	/**
	 * Sets the new attitude for the given user
	 *
	 * @return the old attitude or null
	 */
	public Attitude registerAttitude(final String userPhone, final Attitude attitude) {
		return userAttitude.put(userPhone, attitude);
	}

	/**
	 * Sets the new additionals for the given user
	 *
	 * @return the old additionals or null
	 */
	public Integer registerAdditionals(final String userPhone, final Integer additionals) {
		return userAdditionals.put(userPhone, additionals);
	}

	public Map<Attitude, Integer> sumAttitudes() {

		final Map<Attitude, Integer> sum = new HashMap<>();

		for (final Attitude attitude : Attitude.values()) {
			sum.put(attitude, 0);
		}

		for (final Attitude attitude : userAttitude.values()) {
			sum.put(attitude, sum.get(attitude) + 1);
		}

		for (final Integer additionalsOfUser : userAdditionals.values()) {
			if (additionalsOfUser > 0) {
				sum.put(Attitude.POSITIVE, sum.get(Attitude.POSITIVE) + additionalsOfUser);
			} else if (additionalsOfUser < 0) {
				sum.put(Attitude.NEGATIVE, sum.get(Attitude.NEGATIVE) + additionalsOfUser);
			}
		}

		return sum;
	}

	public void addUserIfNotExists(final User user) {
		users.putIfAbsent(user.getPhone(), user);
		userAttitude.putIfAbsent(user.getPhone(), Attitude.UNKOWN);
		userAdditionals.putIfAbsent(user.getPhone(), 0);
	}

	public void resetVotes() {
		votingSince = new Date();
		userAttitude.replaceAll((k, v) -> Attitude.UNKOWN);
		userAdditionals.replaceAll((k, v) -> 0);
	}

	public List<User> usersWithAttitude(final Attitude attitude) {
		return userAttitude.entrySet().stream()//
				.filter((entry) -> entry.getValue() == attitude) //
				.map((entry) -> users.get(entry.getKey())) //
				.collect(toList());
	}
}
