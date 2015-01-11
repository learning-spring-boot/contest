package com.maciejwalkowiak.mercury.core;

public interface Messenger {
	MercuryMessage publish(Request request);
	void messageSent(MercuryMessage message);
	void deliveryFailed(MercuryMessage message, String errorMessage);
}
