package de.votesapp.groups;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.actuate.metrics.CounterService;

import reactor.core.Reactor;
import reactor.event.Event;
import de.votesapp.client.GroupMessage;
import de.votesapp.commands.plugins.ResetCommandPlugin;
import de.votesapp.commands.plugins.SetAdditionalsCommandPlugin;
import de.votesapp.commands.plugins.SetAttitudeCommandPlugin;
import de.votesapp.commands.plugins.StatusCommandPlugin;

public class GroupMessageListenerTest {

	Group group = new Group("TstGroup");
	GroupMessageListener groupMessageListener;
	GroupService groupService = mock(GroupService.class);
	Reactor reactor = mock(Reactor.class);
	String senderPhone = "2398472893479";

	@Before
	public void setup() {
		groupMessageListener = new GroupMessageListener(Arrays.asList( //
				new SetAttitudeCommandPlugin(), //
				new SetAdditionalsCommandPlugin(), //
				new StatusCommandPlugin(), //
				new ResetCommandPlugin()),//
				groupService, //
				reactor, mock(CounterService.class));
		when(groupService.createOrLoadGroup(any())).thenReturn(group);
		when(groupService.save(group)).thenReturn(group);
	}

	@Test
	public void should_save_to_db_when_no_command_is_given() {
		groupMessageListener.onGroupReceiveMessage(createMessage("Hello World"));
		verify(groupService).save(anyObject());
	}

	@Test
	public void should_add_user_on_new_messages() {
		final Group group = spy(new Group("TstGroup"));

		when(groupService.createOrLoadGroup("TstGroup")).thenReturn(group);

		final GroupMessage message = new GroupMessage("1", "TstGroup", "491234", null, "Hello World");
		groupMessageListener.onGroupReceiveMessage(Event.wrap(message));

		verify(group).addUserIfNotExists(message.sender());
	}

	@Test
	public void should_be_quite_on_normal_test() throws Exception {
		groupMessageListener.onGroupReceiveMessage(createMessage("In winter it snows"));
		// reactor not invoked;
	}

	@Test
	public void should_not_find_numbers_surronded_by_text() throws Exception {
		groupMessageListener.onGroupReceiveMessage(createMessage("It's currently +4 Degress "));
		// reactor not invoked;
	}

	@Test
	public void should_extract_positiv_numbers() throws Exception {
		groupMessageListener.onGroupReceiveMessage(createMessage("+4"));
		assertThat(group.getUserAdditionals().get(senderPhone), is(4));
	}

	@Test
	public void should_extract_negativ_numbers() throws Exception {
		groupMessageListener.onGroupReceiveMessage(createMessage("-4"));
		assertThat(group.getUserAdditionals().get(senderPhone), is(-4));
	}

	private Event<GroupMessage> createMessage(final String text) {
		return Event.wrap(GroupMessage.of("1", "1833-4142", senderPhone, "JUnit", text));
	}

	@Test
	public void should_not_fail_on_large_numbers() throws Exception {
		groupMessageListener.onGroupReceiveMessage(createMessage("+9999999999"));
		// reactor not invoked;
	}
	// @Test
	// public void should_trim() throws Exception {
	// groupMessageListener.onGroupReceiveMessage(Event.wrap(GroupMessage.of(senderPhone,
	// "  +4 "));
	// assertThat(group.getUserAdditionals().get(senderPhone), is(4));
	// }
	//
	// @Test
	// public void should_be_case_insensitive() throws Exception {
	// groupMessageListener.onGroupReceiveMessage(Event.wrap(GroupMessage.of(senderPhone,
	// "yEs"));
	// }
	//
	// @Test
	// public void should_normalize_spaces() throws Exception {
	// groupMessageListener.onGroupReceiveMessage(Event.wrap(GroupMessage.of(senderPhone,
	// "  Bin    dabei   ")); I'm in
	// is(Optional.of(new
	// SetAttitudeCommandPlugin.SetAttitudeCommand(POSITIVE)));
	// }
}
