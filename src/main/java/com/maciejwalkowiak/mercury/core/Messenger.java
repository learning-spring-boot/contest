package com.maciejwalkowiak.mercury.core;

public interface Messenger {
	MercuryMessage publish(Request request);
}
