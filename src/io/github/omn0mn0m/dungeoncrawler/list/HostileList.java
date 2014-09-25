package io.github.omn0mn0m.dungeoncrawler.list;

import io.github.omn0mn0m.dungeoncrawler.Main;
import io.github.omn0mn0m.dungeoncrawler.entity.Hostile;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a list of all the hostiles in the game.
 * @author Nam Tran
 *
 */
public class HostileList {
    
    private String[] keys;	// List of keys for each hostile
    private Hostile[] values;	// List of hostiles
    private Map<String, Hostile> hostileMap;	// Map of keys and which hostile they correspond to
    
    private String fileName = Main.rootPath + "Hostile.nam";	// Name of the file for the list of hostiles
    
    private int totalHostiles = 0;	// Total attacks in the game
    
    /**
	 * Constructor that loads up the list
	 */
	public HostileList() {
		totalHostiles = Main.namReader.getTotal(fileName);
		this.loadKeys();
		this.loadValues();
		this.mapHostiles();
	}
	
	/**
	 * Returns a hostile that corresponds to a specified key.
	 * @param key
	 * @return
	 */
	public Hostile getHostile(String key) {
		return hostileMap.get(key);
	}
	
	/**
	 * Loads every key in the hostile file and assigns it to an array.
	 */
	public void loadKeys() {
		keys = new String[totalHostiles];
		Main.namReader.loadFile(fileName);
		for (int i = 0; i < totalHostiles; i++) {
			Main.namReader.findData(i + "-Key");
			keys[i] = Main.namReader.getStringData();
		}
		Main.namReader.unloadFile();
	}
	
	/**
	 * Loads every hostile in the hostile list and assigns it to an array.
	 */
	public void loadValues() {
		values = new Hostile[totalHostiles];
		Main.namReader.loadFile(fileName);
		for (int i = 0; i < totalHostiles; i++) {
			Main.namReader.findData(String.valueOf(i + "-Name"));
	    	String name = Main.namReader.getStringData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-Health"));
	    	int health = Main.namReader.getIntData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-Attack"));
	    	int attack = Main.namReader.getIntData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-Defense"));
	    	int defense = Main.namReader.getIntData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-XP"));
	    	int xp = Main.namReader.getIntData();
	    	
			values[i] = new Hostile(name, health, defense, attack, xp);
		}
	}
	
	/**
	 * Matches the keys array and the hostiles array to a map.
	 */
	public void mapHostiles() {
		hostileMap  = new HashMap<String, Hostile>(totalHostiles);
		for (int i = 0; i < totalHostiles; i++) {
			hostileMap.put(keys[i], values[i]);
		}
	}
	
	/**
	 * Returns the total hostiles in the game.
	 * @return total hostiles
	 */
	public int getTotalHostiles() {
		return totalHostiles;
	}
	
	/**
	 * Gets the key based on where it appears in the hostiles file.
	 * @param number
	 * @return
	 */
	public String getKey(int number) {
		return keys[number];
	}
}
