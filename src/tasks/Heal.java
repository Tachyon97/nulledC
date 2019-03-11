package tasks;

import org.FirstScript;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;

public class Heal extends Node {

	private Item food;

	@Override
	public int execute() {
		if(food.interact("Eat")) {
			if(FirstScript.listener.getInventoryState() == 0) {
				try {
					FirstScript.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return 2000;
	}

	@Override
	public boolean validate() {
		food = Inventory.getFirst("Lobster");
		return Players.getLocal().getHealthPercent() < 60 && Inventory.contains("Monkfish") && food != null;
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public boolean finishedTask() {
		// TODO Auto-generated method stub
		return false;
	}

}
