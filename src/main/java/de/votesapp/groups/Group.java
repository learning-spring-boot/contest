package de.votesapp.groups;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.springframework.data.annotation.Id;

import de.votesapp.client.User;
import de.votesapp.parser.Attitude;

@Data
public class Group {
	@Id
	private final String groupId;

	private final Map<String, User> users = new HashMap<>();

	private final Map<String, Attitude> userAttitude = new HashMap<>();

	private final Map<String, Integer> userAdditionals = new HashMap<>();

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

		int additionals = 0;
		for (final Integer additionalsOfUser : userAdditionals.values()) {
			additionals += additionalsOfUser;
		}
		sum.put(Attitude.POSITIVE, sum.get(Attitude.POSITIVE) + additionals);

		return sum;
	}

	public void addUserIfNotExists(final User user) {
		users.putIfAbsent(user.getPhone(), user);
		userAttitude.putIfAbsent(user.getPhone(), Attitude.UNKOWN);
		userAdditionals.putIfAbsent(user.getPhone(), 0);
	}

	public void resetVotes() {
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
