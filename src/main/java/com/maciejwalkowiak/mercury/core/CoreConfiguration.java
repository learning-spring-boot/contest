package com.maciejwalkowiak.mercury.core;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan
class CoreConfiguration {
	@Autowired
	@Listener
	private List<Object> listeners;

	@Bean
	MBassador<Request> bus() {
		MBassador<Request> bus = new MBassador<>(new BusConfiguration()
				.addFeature(Feature.SyncPubSub.Default())
				.addFeature(Feature.AsynchronousHandlerInvocation.Default())
				.addFeature(Feature.AsynchronousMessageDispatch.Default()));

		bus.addErrorHandler(new Slf4jPublicationErrorHandler());

		listeners.forEach(bus::subscribe);

		return bus;
	}
}

class Slf4jPublicationErrorHandler implements IPublicationErrorHandler {
	private static final Logger LOG = LoggerFactory.getLogger(Slf4jPublicationErrorHandler.class);

	@Override
	public void handleError(PublicationError error) {
		LOG.error("Publication failed: " + error.getMessage(), error.getCause());
	}
}
