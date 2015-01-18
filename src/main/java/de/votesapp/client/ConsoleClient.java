package de.votesapp.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import javax.annotation.PostConstruct;

import lombok.SneakyThrows;

import org.springframework.context.annotation.Profile;

/**
 * This makes it run on a developers pc without a connection to yowsup. Later we
 * could replace this by an more elegant ui.
 */
// @Component
@Profile("!yowsup")
public class ConsoleClient implements WhatsAppClient {

	private final List<GroupMessage> groupMessages = new ArrayList<>();

	@Override
	public void sendGroupMessage(final GroupMessage messageToSend) {
		System.out.println(">>> Group: " + messageToSend.getGroupId() + ": " + messageToSend.getText());
	}

	@Override
	public GroupMessage[] fetchGroupMessages() {
		synchronized (groupMessages) {
			final GroupMessage[] messages = groupMessages.toArray(new GroupMessage[0]);
			groupMessages.clear();
			return messages;
		}
	}

	@PostConstruct
	public void waitForInput() {
		new Thread(new Reader()).start();
	}

	private class Reader implements Runnable {

		private final Scanner scanner = new Scanner(System.in);
		private String lastPhone = "49111222333";
		private String lastGroup = "Fussball";
		private String lastText = "In";

		@SneakyThrows
		@Override
		public void run() {
			// That should be triggerd when the whole context is spawned.
			// So we need to wait for the Application Startup event.
			// Otherwise you don't see the output
			Thread.sleep(10_000);

			while (true) {
				System.out.println("==== Enter new Message ===");

				Optional<String> input = read("Phone", lastPhone);
				if (input.isPresent()) {
					lastPhone = input.get();
				} else {
					continue;
				}

				input = read("Group", lastGroup);
				if (input.isPresent()) {
					lastGroup = input.get();
				} else {
					continue;
				}

				input = read("Text", lastText);
				if (input.isPresent()) {
					lastText = input.get();
				} else {
					continue;
				}

				synchronized (groupMessages) {
					final GroupMessage generatedMessage = new GroupMessage(UUID.randomUUID().toString(), lastGroup, lastPhone, null, lastText);
					System.out.println("<<< " + generatedMessage);
					groupMessages.add(generatedMessage);
				}
			}
		}

		/**
		 * Read from stdin and return the value if possible
		 *
		 * @param message
		 * @param defaultValue
		 * @return empty() when no input was given and the action should be
		 *         reseted.
		 */
		private Optional<String> read(final String message, final String defaultValue) {
			System.out.print(message + " (r=reset, d=\"" + defaultValue + "\"): ");
			final String line = scanner.next();

			switch (line) {
			case "r":
				Optional.empty();
			case "d":
				return Optional.of(defaultValue);
			default:
				return Optional.of(line);
			}
		}
	}
}
