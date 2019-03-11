package org.listener;

import org.rspeer.runetek.api.component.tab.Inventory;

public class InventoryListener {
	
	int getCount = 0;
	
	public void setInventoryCount(int i) {
		getCount = i;
	}
	
	public int getInventoryState() {
		int changed = 0;
		int getSecondCount = Inventory.getCount();
		if(getSecondCount != getCount) {
			changed = 1;
		}
		return changed;
	}

}
