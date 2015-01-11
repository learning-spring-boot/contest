package de.votesapp.groups;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.parser.HumanMessageParser;

public class GroupMessageListenerTest {

	GroupMessageListener groupMessageListener;
	HumanMessageParser parser = mock(HumanMessageParser.class);
	GroupService groupService = mock(GroupService.class);
	Reactor reactor = mock(Reactor.class);

	@Before
	public void setup() {
		groupMessageListener = new GroupMessageListener(parser, groupService, reactor);
	}

	@Test
	public void testName() throws Exception {
	}

	@Test
	public void should_save_to_db_when_no_command_is_given() {
		when(groupService.createOrLoadGroup("TstGroup")).thenReturn(new Group("TstGroup"));
		when(parser.parse("Hello World")).thenReturn(Optional.empty());

		groupMessageListener.onGroupReceiveMessage(Event.wrap(new GroupMessage("1", "TstGroup", "491234", "Hello World")));

		verify(groupService).save(anyObject());
	}

	@Test
	public void should_add_user_on_new_messages() {

		final Group group = spy(new Group("TstGroup"));

		when(groupService.createOrLoadGroup("TstGroup")).thenReturn(group);
		when(parser.parse("Hello World")).thenReturn(Optional.empty());

		groupMessageListener.onGroupReceiveMessage(Event.wrap(new GroupMessage("1", "TstGroup", "491234", "Hello World")));

		verify(group).addUserIfNotExists("491234");
	}
}
