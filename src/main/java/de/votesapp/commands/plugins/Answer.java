package de.votesapp.commands.plugins;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;

/**
 * An {@link Answer} that gets created for and received {@link GroupMessage}.
 *
 * {@link AbstractCommandPlugin}s are using those as Returntype. Currently only
 * answers into Groups are supported. But it's also possible to override the
 * invoke message and send messages to individuals.
 */
public abstract class Answer {

	public abstract void invoke(final Group group, final Reactor reactor, final GroupMessage message);

	/**
	 * Creates an answer that sends the given message into the given group.
	 */
	public static Answer intoGroup(final Group group, final String message) {
		return new Answer() {
			@Override
			public void invoke(final Group group, final Reactor reactor, final GroupMessage groupMessage) {
				reactor.notify("group.outbox", Event.wrap(GroupMessage.of(group.getGroupId(), message)));
			}
		};
	}

}
