package com.maciejwalkowiak.mercury.core;

import org.springframework.context.ApplicationEvent;

public class MercuryEvent<T extends Request> extends ApplicationEvent {
	private MercuryMessage<T> mercuryMessage;

	public MercuryEvent(MercuryMessage<T> mercuryMessage) {
		super(MercuryEvent.class);
		this.mercuryMessage = mercuryMessage;
	}

	public T getRequest() {
		return mercuryMessage.getRequest();
	}
}
