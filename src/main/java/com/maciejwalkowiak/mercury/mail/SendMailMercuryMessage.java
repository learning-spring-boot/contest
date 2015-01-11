package com.maciejwalkowiak.mercury.mail;

import com.maciejwalkowiak.mercury.core.MercuryMessage;

class SendMailMercuryMessage extends MercuryMessage<SendMailRequest> {
	SendMailMercuryMessage(Status status, SendMailRequest request) {
		super(status, request);
	}
}
