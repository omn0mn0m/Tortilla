package io.github.omn0mn0m.tortilla.entity;


public class Npc extends Entity {
	
	private int relationship = 0;

	public Npc(String name) {
		this.name = name;
	}
	
	public int getRelationship() {
		return relationship;
	}
	
	public void setRelationship(int increment) {
		relationship += increment;
	}
}
