package de.votesapp;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import reactor.core.Reactor;
import reactor.event.Event;
import reactor.event.selector.Selectors;
import de.votesapp.client.GroupMessage;
import de.votesapp.groups.Group;
import de.votesapp.groups.GroupService;
import de.votesapp.parser.Attitude;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VotesAppApplication.class)
@WebAppConfiguration
// TODO: thats an integration test. We haven't configured surefire yet. Rename
// to ...IT
public class VotesAppUserTests_Test {

	@Autowired
	Reactor reactor;

	@Autowired
	GroupService groupService;

	@Test
	public void should_regonize_a_vote() throws InterruptedException {

		reactor.notify("group.inbox", Event.wrap(new GroupMessage("0", "Test", "490000", null, "Yes")));

		// TODO: Don't know how to wait until the event is processed?
		Thread.sleep(1000);

		final Group group = groupService.createOrLoadGroup("Test");

		assertThat(group.getUserAttitude().get("490000"), is(Attitude.POSITIVE));
	}

	@Test(timeout = 1_000)
	public void some_positive_and_negative_votes() throws InterruptedException {

		final AtomicBoolean success = new AtomicBoolean(false);

		// expect
		reactor.on(Selectors.$("group.outbox"), e -> {
			synchronized (reactor) {
				success.compareAndSet(false, GroupMessage.of("Test", //
						MessageFormat.format("{0}: 1\n" //
								+ "- 491111\n" //
								+ "{1}: 1\n" //
								+ "- 492222\n" //
								+ "{2}: 2\n" //
								+ "- 490000\n" //
								+ "- 493333", Attitude.POSITIVE.getIcon(), Attitude.NEGATIVE.getIcon(), Attitude.UNKOWN.getIcon())).equals(e.getData()));
				reactor.notifyAll();
			}
		});

		// given
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("0", "Test", "490000", null, "Hello There")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("1", "Test", "491111", null, "Yes")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("2", "Test", "492222", null, "No")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("3", "Test", "493333", null, "Status")));

		// wait
		synchronized (reactor) {
			while (!success.get()) {
				reactor.wait();
			}
		}

		assertThat(success.get(), is(true));
	}

	@Test(timeout = 1_000)
	public void reset_a_given_vote() throws InterruptedException {

		final AtomicBoolean success = new AtomicBoolean(false);

		// expect
		reactor.on(Selectors.$("group.outbox"), e -> {
			synchronized (reactor) {
				success.compareAndSet(false, GroupMessage.of("Test", MessageFormat.format("{0}: 1\n" //
						+ "- 492222\n" //
						+ "{1}: 0\n" //
						+ "{2}: 3\n" //
						+ "- 490000\n" //
						+ "- 491111\n" //
						+ "- 493333", Attitude.POSITIVE.getIcon(), Attitude.NEGATIVE.getIcon(), Attitude.UNKOWN.getIcon())).equals(e.getData()));

				reactor.notifyAll();
			}
		});

		// given
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("0", "Test", "490000", null, "Hello There")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("1", "Test", "491111", null, "Yes")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("2", "Test", "492222", null, "No")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("3", "Test", "492222", null, "Reset")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("4", "Test", "492222", null, "Yes")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("5", "Test", "493333", null, "Status")));

		// wait
		synchronized (reactor) {
			while (!success.get()) {
				reactor.wait();
			}
		}

		assertThat(success.get(), is(true));
	}
}
