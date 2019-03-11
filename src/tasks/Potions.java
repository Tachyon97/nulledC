package tasks;

import org.FirstScript;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.component.tab.Inventory;

public class Potions extends Node {

	String potion;

	public Potions(String potion) {
		this.potion = potion;
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public boolean validate() {
		return FirstScript.enable_potions;
	}

	@Override
	public int execute() {
		for (Item i : Inventory.getItems()) {
			if (Inventory.contains(potion)) {
				i.click();
			}
		}
		return 300;
	}

	@Override
	public boolean finishedTask() {
		return false;
	}

}
