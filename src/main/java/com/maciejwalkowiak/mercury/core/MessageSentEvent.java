package com.maciejwalkowiak.mercury.core;

import org.springframework.context.ApplicationEvent;

public class MessageSentEvent<T extends Request> extends ApplicationEvent {
	private final MercuryMessage<T> mercuryMessage;

	public MessageSentEvent(MercuryMessage<T> mercuryMessage) {
		super(MessageSentEvent.class);
		this.mercuryMessage = mercuryMessage;
	}

	public MercuryMessage<T> getMercuryMessage() {
		return mercuryMessage;
	}
}
