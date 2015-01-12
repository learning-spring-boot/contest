package de.votesapp.client;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "name")
public class User {
	private String phone;
	private String name;

	/**
	 * @return returns {@link #senderName} if it isn't blank. Otherwise the
	 *         {@link #phone}. If the phone is null as will, "" is returned.
	 */
	public String nameOrPhone() {
		if (isNotBlank(name)) {
			return name;
		}

		if (isNotBlank(phone)) {
			return phone;
		}
		return "";
	}

}
