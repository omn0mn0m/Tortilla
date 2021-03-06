package io.github.omn0mn0m.tortilla.list;

import io.github.omn0mn0m.tortilla.Main;
import io.github.omn0mn0m.tortilla.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a list of all the items in the game.
 * @author Nam Tran
 *
 */
public class ItemList {
	
    private String[] keys;	// List of keys for each item
    private Item[] values;	// List of items
    private Map<String, Item> itemsMap;	// Map of keys and which item they correspond to
    
    private String fileName = Main.rootPath + "Items.nam";	// Name of the file for the list of items
    
    private int totalItems = 0;		// Total items in the game
    
    /**
	 * Constructor that loads up the list
	 */
	public ItemList() {
		totalItems = Main.namReader.getTotal(fileName);
		this.loadKeys();
		this.loadValues();
		this.mapItems();
	}
	
	/**
	 * Returns an item that corresponds to a specified key.
	 * @param key
	 * @return item
	 */
	public Item getItem(String key) {
		return itemsMap.get(key);
	}
	
	/**
	 * Loads every key in the items file and assigns it to an array.
	 */
	public void loadKeys() {
		keys = new String[totalItems];
		Main.namReader.loadFile(fileName);
		for (int i = 0; i < totalItems; i++) {
			Main.namReader.findData(i + "-Key");
			keys[i] = Main.namReader.getStringData().toLowerCase();
		}
		Main.namReader.unloadFile();
	}
	
	/**
	 * Loads every item in the items list and assigns it to an array.
	 */
	public void loadValues() {
		values = new Item[totalItems];
		Main.namReader.loadFile(fileName);
		for (int i = 0; i < totalItems; i++) {
			Main.namReader.findData(String.valueOf(i + "-Name"));
	    	String name = Main.namReader.getStringData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-Type"));
	    	String type = Main.namReader.getStringData().toLowerCase();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-AttackBuff"));
	    	int attackBuff = Main.namReader.getIntData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-DefenseBuff"));
	    	int defenseBuff = Main.namReader.getIntData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-HealthBuff"));
	    	int healthBuff = Main.namReader.getIntData();
	    	
			values[i] = new Item(name, type, attackBuff, defenseBuff, healthBuff);
		}
		Main.namReader.unloadFile();
	}
	
	/**
	 * Matches the keys array and the items array to a map.
	 */
	public void mapItems() {
		itemsMap  = new HashMap<String, Item>(totalItems);
		for (int i = 0; i < totalItems; i++) {
			itemsMap.put(keys[i], values[i]);
		}
	}
	
	/**
	 * Gets the key based on where it appears in the items file.
	 * @param number
	 * @return key
	 */
	public String getKey(int number) {
		return keys[number];
	}
	
	/**
	 * Returns the number of total items in the game.
	 * @return total items
	 */
	public int getTotalItems() {
		return totalItems;
	}
}
