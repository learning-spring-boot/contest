package de.votesapp.parser.plugins;

public interface Describable {

	public int TOP = Integer.MIN_VALUE + 10000;
	public int VOTE = 1000;
	public int TOOLS = 2000;
	public int EASTEREGGS = 3000;
	public int BOTTOM = Integer.MAX_VALUE - 10000;

	public int getPriority();

	public String getName();

	public String[] getTriggers();

	public String getDescription();
}
