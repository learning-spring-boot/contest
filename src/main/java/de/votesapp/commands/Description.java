package de.votesapp.commands;

import lombok.Value;

@Value
public class Description {
	int priority;
	String name;
	String[] trigger;
	String description;

	public static class DescriptionBuilder {
		private final String name;
		private String[] trigger;
		private String description;
		private int priority;

		private DescriptionBuilder(final String name) {
			this.name = name;
		}

		public static DescriptionBuilder describe(final String name) {
			return new DescriptionBuilder(name);
		}

		public DescriptionBuilder withTrigger(final String... trigger) {
			this.trigger = trigger;
			return this;
		}

		public DescriptionBuilder withDescription(final String description) {
			this.description = description;
			return this;
		}

		public DescriptionBuilder onPosition(final int priority) {
			this.priority = priority;
			return this;
		}

		public Description done() {
			return new Description(priority, name, trigger, description);
		}
	}
}
