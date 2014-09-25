package io.github.omn0mn0m.tortilla.entity;

import io.github.omn0mn0m.tortilla.Dream;
import io.github.omn0mn0m.tortilla.item.Inventory;
import io.github.omn0mn0m.tortilla.item.Item;

public class Trader extends Entity {
	
	private String name;
	
	private Inventory inventory;

	public Trader(String name, int invSize) {
		inventory = new Inventory(invSize);
		this.name = name;
		fillInventory();
		 
		Dream.print("Hey there! I'm " + this.name + ". You can trade with me.");
	}
	
	public void checkInventory() {
		inventory.checkInventory(true);
	}
	
	public void exchangeItems(String playerItem, String traderItem, Hero hero) {
		if (hero.hasInventoryItem(playerItem) && inventory.hasItem(traderItem)) {
			hero.removeItem(inventory, playerItem);
			hero.addItem(inventory, traderItem);
		} else {
			Dream.print("Something was missing in the transaction!");
		}
	}
	
	public void fillInventory() {
		for (int i = 0; i < inventory.getSize(); i++) {
    		inventory.addItem(new Item(Dream.itemList.getItem(Dream.itemList.getKey(random.nextInt(Dream.itemList.getTotalItems())))));
		}
	}
}
