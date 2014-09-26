package io.github.omn0mn0m.tortilla;

import io.github.omn0mn0m.tortilla.entity.Hero;
import io.github.omn0mn0m.tortilla.entity.Npc;

public class Characters {

	// Tortilla
	public Hero hero;
	public Npc tori;
	public Npc nam;
	public Npc nick;
	
	// Evil Exes
	public Npc willow;
	public Npc steve;
	
	// Supporting Characters
	public Npc mg;
	
	public Characters() {
		//Tortilla
		hero = new Hero();
		tori = new Npc("Tori");
		nam = new Npc("Nam");
		nick = new Npc("Nick");
		
		// Evil Exes
		willow = new Npc("Willow");
		steve = new Npc("Steve");
		mg = new Npc("Mg");
	}
}
