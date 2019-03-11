package org.api;

public enum Equipment {
	/*
	 * House must contain Mounted Glory
	 * House must contain Varrock portal
	 * Varrock achievement Diary Medium must be completed
	 */
	House_Tab("House tab"),
	Dramen_Staff("Dramen staff"),
	Coins("Coins"),
	Games_Necklace("Games Necklace (8)");
	
	public String equipment;
	
	Equipment(String name) {
		equipment = name;
	}

}
