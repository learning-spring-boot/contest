package de.votesapp;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VotesAppApplication.class)
@WebAppConfiguration
// TODO: thats an integration test. We haven't configured surefire yet. Rename
// to ...IT
public class VotesAppUserTests_Test {

	@Autowired
	Reactor reactor;

	@Test(timeout = 1_000)
	public void some_positive_and_negative_votes() throws InterruptedException {

		final AtomicBoolean success = new AtomicBoolean(false);

		// expect
		reactor.on(Selectors.$("group.outbox"), e -> {
			synchronized (reactor) {
				success.compareAndSet(false, GroupMessage.of("Test", "*thumb_up*: 1\n*thumb_down*: 1\n*questionmark*: 2").equals(e.getData()));
				reactor.notifyAll();
			}
		});

		// given
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("0", "Test", "490000", "Hello There")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("1", "Test", "491111", "Yes")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("2", "Test", "492222", "No")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("3", "Test", "493333", "Status")));

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
				success.compareAndSet(false, GroupMessage.of("Test", "*thumb_up*: 1\n*thumb_down*: 0\n*questionmark*: 3").equals(e.getData()));
				reactor.notifyAll();
			}
		});

		// given
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("0", "Test", "490000", "Hello There")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("1", "Test", "491111", "Yes")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("2", "Test", "492222", "No")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("3", "Test", "492222", "Reset")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("4", "Test", "492222", "Yes")));
		reactor.notify("group.inbox", Event.wrap(new GroupMessage("5", "Test", "493333", "Status")));

		// wait
		synchronized (reactor) {
			while (!success.get()) {
				reactor.wait();
			}
		}

		assertThat(success.get(), is(true));
	}
}
