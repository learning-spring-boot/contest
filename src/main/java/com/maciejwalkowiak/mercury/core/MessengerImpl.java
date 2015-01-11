package com.maciejwalkowiak.mercury.core;

import net.engio.mbassy.bus.MBassador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class MessengerImpl implements Messenger {
	private final MBassador<MercuryMessage> bus;
	private final MercuryMessageRepository mercuryMessageRepository;

	@Autowired
	MessengerImpl(MBassador<MercuryMessage> bus, MercuryMessageRepository mercuryMessageRepository) {
		this.bus = bus;
		this.mercuryMessageRepository = mercuryMessageRepository;
	}

	@Override
	public MercuryMessage publish(MercuryMessage mercuryMessage) {
		mercuryMessageRepository.save(mercuryMessage);

		bus.publishAsync(mercuryMessage);

		return mercuryMessage;
	}
}
