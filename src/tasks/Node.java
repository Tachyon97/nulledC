package tasks;

public abstract class Node {
	public abstract int priority();

	public abstract boolean validate();

	public abstract int execute();

	public abstract boolean finishedTask();
	public int getKills() {
		return 0;
	}
}
