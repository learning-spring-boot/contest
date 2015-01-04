package de.votesapp.groups;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

import org.springframework.data.annotation.Id;

import de.votesapp.parser.Attitude;

@Data
public class Group {
	@Id
	private final String groupId;

	private final Map<String, Attitude> userAttitude = new HashMap<>();

	private final Map<String, Integer> userAdditionals = new HashMap<>();

	/**
	 * Sets the new attitude for the given user
	 *
	 * @return the old attitude or null
	 */
	public Attitude registerAttitude(final String user, final Attitude attitude) {
		return userAttitude.put(user, attitude);
	}

	/**
	 * Sets the new additionals for the given user
	 *
	 * @return the old additionals or null
	 */
	public Integer registerAdditionals(final String user, final Integer additionals) {
		return userAdditionals.put(user, additionals);
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
}
