package com.maciejwalkowiak.mercury.slack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maciejwalkowiak.mercury.core.Request;

import javax.validation.constraints.NotNull;

class SlackRequest extends Request {
	@NotNull
	private final String text;
	private final String username;
	private final String iconUrl;
	private final String iconEmoji;
	private final String channel;

	@JsonCreator
	public SlackRequest(@JsonProperty("text") String text,
						@JsonProperty("username") String username,
						@JsonProperty("icon_url") String iconUrl,
						@JsonProperty("icon_emoji") String iconEmoji,
						@JsonProperty("channel") String channel) {
		this.text = text;
		this.username = username;
		this.iconUrl = iconUrl;
		this.iconEmoji = iconEmoji;
		this.channel = channel;
	}

	public String getText() {
		return text;
	}

	public String getUsername() {
		return username;
	}

	@JsonProperty("icon_url")
	public String getIconUrl() {
		return iconUrl;
	}

	@JsonProperty("icon_emoji")
	public String getIconEmoji() {
		return iconEmoji;
	}

	public String getChannel() {
		return channel;
	}

	@Override
	public String toString() {
		return "SlackRequest{" +
				"text='" + text + '\'' +
				", username='" + username + '\'' +
				", iconUrl='" + iconUrl + '\'' +
				", iconEmoji='" + iconEmoji + '\'' +
				", channel='" + channel + '\'' +
				'}';
	}
}
