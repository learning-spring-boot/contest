package com.maciejwalkowiak.mercury.core;

import net.engio.mbassy.bus.MBassador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class MessengerImpl implements Messenger {
	private final MBassador<Request> bus;

	@Autowired
	MessengerImpl(MBassador<Request> bus) {
		this.bus = bus;
	}

	@Override
	public MercuryMessage publish(Request request) {
		bus.publishAsync(request);

		return MercuryMessage.queued(request);
	}
}
