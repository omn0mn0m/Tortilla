package io.github.omn0mn0m.tortilla.list;

import io.github.omn0mn0m.tortilla.Main;
import io.github.omn0mn0m.tortilla.entity.Attack;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a list of all the attacks in the game.
 * @author Nam Tran
 *
 */
public class AttackList {
	
	private String[] keys;	// List of keys for each attack
	private Attack[] values;	// List of attacks
	private Map<String, Attack> attacksMap;	// Map of keys and which attack they correspond to
	
	private String fileName = Main.rootPath + "Attacks.nam";	// Name of the file for the list of attacks
	
	private int totalAttacks = 0;	// Total attacks in the game
	
	/**
	 * Constructor that loads up the list
	 */
	public AttackList() {
		totalAttacks = Main.namReader.getTotal(fileName);
		this.loadKeys();
		this.loadValues();
		this.mapAttacks();
	}

	/**
	 * Returns an attack that corresponds to a specified key.
	 * @param key
	 * @return attack
	 */
	public Attack getAttack(String key) {
		return attacksMap.get(key);
	}
	
	/**
	 * Loads every key in the attack file and assigns it to an array.
	 */
	public void loadKeys() {
		keys = new String[totalAttacks];
		Main.namReader.loadFile(fileName);
		for (int i = 0; i < totalAttacks; i++) {
			Main.namReader.findData(i + "-Key");
			keys[i] = Main.namReader.getStringData().toLowerCase();
		}
		Main.namReader.unloadFile();
	}
	
	/**
	 * Loads every attack in the attack list and assigns it to an array.
	 */
	public void loadValues() {
		values = new Attack[totalAttacks];
		Main.namReader.loadFile(fileName);
		for (int i = 0; i < totalAttacks; i++) {
			Main.namReader.findData(String.valueOf(i + "-Name"));
	    	String name = Main.namReader.getStringData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-Requires"));
	    	String requires = Main.namReader.getStringData();
	    	
	    	Main.namReader.findData(String.valueOf(i + "-AttackBuff"));
	    	int attackBuff = Main.namReader.getIntData();
	    	
			values[i] = new Attack(name, requires, attackBuff);
		}
		Main.namReader.unloadFile();
	}
	
	/**
	 * Matches the keys array and the attacks array to a map.
	 */
	public void mapAttacks() {
		attacksMap  = new HashMap<String, Attack>(totalAttacks);
		for (int i = 0; i < totalAttacks; i++) {
			attacksMap.put(keys[i], values[i]);
		}
	}
}
