package org.api;

public enum Nieve {
	
	ABSPE("Aberrant spectre", 60, "Deviant spectres"),
	ABDE("Abyssal demon", 85, "none"),
	ADDR("Adamant dragon", 1, "none"),
	ANKOU("Ankou", 1, "Dark Ankou"),
	AVIANSIES("Aviansie", 1, "several"),
	BLDE("Black demons", 1, "several"),
	BLDR("Black dragons", 1, "several"),
	BLOODVELDS("Bloodvelds", 50, "Mutated Bloodveld"),
	BLUE_DR("Blue dragons", 1, "several"),
	CAVE_HORRORS("Cave horror", 58, "non"),
	CAVE_KRAKEN("Cave kraken", 87, "Kraken"),
	FIRE_GIANTS("Fire giant", 1, "none"),
	GARGOYLES("Gargoyle", 75, "Grotesque Guardians"),
	HELLHOUNDS("Hellhound", 1, "several"),
	KALPHITES("Kalphite Worker", 1, "several"),
	GRDE("Greater demon", 1, "several"),
	TROLLS("Troll", 1, "several"),
	TUROTH("Turoth", 55, "none");
	private String task_Name;
	private String alternates;
	private int required_Level;
	
	Nieve(String n, int l, String a) {
		task_Name = n;
		required_Level = l;
		alternates = a;
	}
	public String getTask() {
		return task_Name;
	}
	public String getAlternates() {
		return alternates;
	}
	public int getRequiredLevel() {
		return required_Level;
	}
	

}
