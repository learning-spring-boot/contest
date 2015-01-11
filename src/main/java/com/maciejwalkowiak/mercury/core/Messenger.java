package com.maciejwalkowiak.mercury.core;

public interface Messenger {
	MercuryMessage publish(MercuryMessage mercuryMessage);
}
