package tasks;

import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;

public class Attack extends Node {

	Npc npc;
	int kills = 0;
	int tasks = 0;
	String name;

	public Attack(String npc, int task_Amount) {
		tasks = task_Amount;
		name = npc;
	}

	@Override
	public int execute() {
		if (!Players.getLocal().isHealthBarVisible())
			if (npc.interact("Attack")) {
				kills++;
				if (Time.sleepUntil(() -> Players.getLocal().getTargetIndex() != -1, 2500) && (true)) {
				}
			}
		return 300;
	}

	@Override
	public boolean validate() {

		npc = Npcs.getNearest(x -> x.getName().equals(name)
				&& (x.getTargetIndex() == -1 || x.getTarget().equals(Players.getLocal())));
		return (npc != null) && !Players.getLocal().isHealthBarVisible() && !Players.getLocal().isMoving()
				&& finishedTask();
	}

	@Override
	public int priority() {
		return 2;
	}

	public int getKills() {
		return kills;
	}

	@Override
	public boolean finishedTask() {
		return kills <= tasks;
	}

}
