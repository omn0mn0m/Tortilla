package io.github.omn0mn0m.tortilla.entity;

import io.github.omn0mn0m.tortilla.Dream;
import io.github.omn0mn0m.tortilla.Main;
import io.github.omn0mn0m.tortilla.item.Inventory;
import io.github.omn0mn0m.tortilla.item.Item;
import io.github.omn0mn0m.util.Input;

/**
 * This is the class for the player.
 * @author Nam Tran
 *
 */
public class Hero extends Entity {
	
	// Inventory sizes
	private final int INVENTORY_SIZE = 9;
	private final int EQUIPPED_SIZE = 2;
	
	// Random Class Stats
	private final int RANDOM_HEALTH = 100;
	private final int RANDOM_ATTACK = 15;
	private final int RANDOM_DEFENSE = 15;
	
	private Inventory inventory = new Inventory(INVENTORY_SIZE);	// Player's inventory
	private Inventory equipped = new Inventory(EQUIPPED_SIZE);		// Player's equip inventory
	private final int WEAPON_SLOT = 0;
	private final int ARMOUR_SLOT = 1;
    
	private Input input = new Input();	// Player's input
    private String playerName;		// Name of the player
	private String playerClass;		// Class of the player

    /**
     * Constructor
     */
    public Hero() {
    	this.name = "You";
    	level = 1;
    }
    
    /**
     * Runs a script to select the player class and change the stats accordingly.
     */
    public void selectName() {
        Main.print("You wake up in your comfortable bed, and as you sit up you see yourself in the mirror.");
        Main.print("Who do you see staring back at you? (Type the name)");
        Main.print("| Nam | Nick | Tori |");
        playerName = input.getSimpleInput(false);
        Main.print("Okay, so you are " + playerName);
        
        String classFilePath = Main.rootPath + "Player Names.nam";
        Main.namReader.loadFile(classFilePath);
    	
        Main.namReader.findData(String.valueOf(playerName + "-Health"));
    	health =  Main.namReader.getIntData();
    	
    	 Main.namReader.findData(String.valueOf(playerName + "-Attack"));
    	attack =  Main.namReader.getIntData();
    	
    	 Main.namReader.findData(String.valueOf(playerName + "-Defense"));
    	defense =  Main.namReader.getIntData();
    	
    	 Main.namReader.unloadFile();
    	
    	if (! Main.namReader.isFoundElement()) {
    		Main.print("That may be someone, but not for this story... Try again!");
            selectName();
    	}
    }
    
    /**
     * Runs a script to select the player class and change the stats accordingly.
     */
    public void selectClass() {
        Main.print("You wake up inside a dungeon. Fight your way out.");
        Main.print("Actually, first you should select a class... (Type the name)");
        Main.print("| Warrior | Rogue | Mage | Healer |");
        playerClass = input.splitAndGetInput(0);
        Main.print("Okay, so you are a " + playerClass);
        
        String classFilePath = Main.rootPath + "Player Classes.nam";
        Main.namReader.loadFile(classFilePath);
    	
        Main.namReader.findData(String.valueOf(playerClass + "-Health"));
    	health = Main.namReader.getIntData();
    	
    	Main.namReader.findData(String.valueOf(playerClass + "-Attack"));
    	attack = Main.namReader.getIntData();
    	
    	Main.namReader.findData(String.valueOf(playerClass + "-Defense"));
    	defense = Main.namReader.getIntData();
    	
    	Main.namReader.unloadFile();
    	
    	if (!Main.namReader.isFoundElement()) {
    		Main.print("That's awkward... your class doesn't actually do anything... Do you still wish to proceed?");
            if (input.splitAndGetInput(0).equals("yes")) {
            	this.setStats(random.nextInt(RANDOM_HEALTH), random.nextInt(RANDOM_ATTACK), random.nextInt(RANDOM_DEFENSE));
            } else {
            	this.selectClass();
            }
    	}
    }

    /**
     * Checks for if the player is dead and ends the game if so.
     */
    public void checkIfAlive() {
    	this.checkHealth();
        if (!alive) {
        	Main.print("You have died.");
            System.exit(0);
        }
    }

    /**
     * Attacks a specified hostile and determines how much damage should be done.
     * @param hostile
     */
    public void attack(Hostile hostile) {
        if (hostile == null) {
        	Main.print("You can't attack what's not there...");
        } else {
        	int attackBuff = 0;
            Main.print("Which attack should you use?");
            String attackType = input.getSimpleInput();
            
            if (Dream.attackList.getAttack(attackType) != null) {
            	if (equipped.checkSlot(WEAPON_SLOT) != null && Dream.attackList.getAttack(attackType).getAttackRequires().equalsIgnoreCase(equipped.checkSlot(WEAPON_SLOT).getName())) {
            		attackBuff = Dream.attackList.getAttack(attackType).getAttackBuff();
            	} else {
            		Main.print("You attack, but a lack of items makes it not as effective...");
            	}
            }
            
        	Main.print("You " + attackType + " the " + hostile.getName() + ".");
            hostile.takeDamage(this, attackBuff);
        }
    }
    
    /**
     * Runs the script to reroll the character's class and stats.
     */
    public void rerollCharacter() {
    	Main.print("Are you sure you wish to reroll your character? This will knock you unconscious...");
        if (input.getSimpleInput().equalsIgnoreCase("yes")) {
            this.selectName();
        } else {
        	Main.print("You have cancelled your rerolling.");
        }
    }
    
    /**
     * Prints the player's inventory, including all empty slots.
     */
    public void checkInventory() {
    	inventory.checkInventory(true);
    }
    
    /**
     * Adds an item to a specific slot in the player's inventory.
     * @param slot
     * @param item
     */
    public void add(int slot, Item item) {
    	inventory.add(slot, item);
    }
    
    /**
     * Removes an item from a specific slot.
     */
    public void removeSlot(int slot) {
    	inventory.removeSlot(slot);
    	Main.print("Slot " + slot + " has been emptied.");
    }
    
    /**
     * Removes an item from the player inventory and adds it to a "donation" inventory.
     * @param donationInventory
     * @param item
     */
    public void removeItem(Inventory donationInventory, String item) {
    	try {
	    	Item targetItem = Dream.itemList.getItem(item);
	    	if (inventory.hasItem(targetItem.getName())) {
	    		inventory.removeItem(targetItem);
	    		donationInventory.addItem(targetItem);
	    		Main.print("The " + item + " has been removed.");
	    	} else {
	    		Main.print("That item can't be removed!");
	    	}
    	} catch (NullPointerException e) {
    		Main.print("That item doesn't exist!");
    	}
    }
    
    /**
     * Adds an item to the player inventory and removes it from a "donor" inventory.
     * @param donorInventory
     * @param item
     */
    public void addItem(Inventory donorInventory, String item) {
    	try {
	    	Item targetItem = Dream.itemList.getItem(item);
	    	if (donorInventory.hasItem(targetItem.getName())) {
	    		inventory.addItem(targetItem);
	    		donorInventory.removeItem(targetItem);
	    		Main.print("The " + item + " has been added.");
	    	} else {
	    		Main.print("That item can't be added!");
	    	}
    	} catch (NullPointerException e) {
    		Main.print("That item doesn't exist!");
    	}
    }
    
    public boolean hasInventoryItem(String item) {
    	return inventory.hasItem(item);
    }
    
    /**
     * Prints the player's equip inventory.
     */
    public void checkEquipped() {
    	equipped.checkInventory(true);
    }
    
    /**
     * Equips an item if the item is equippable and the slot is empty
     * @param item
     */
    public void equipItem(String item) {
    	try {
    		Item targetItem = Dream.itemList.getItem(item);
        	int targetSlot = -1;
        	
	    	if (targetItem.getType().equals("weapon")) {
	    		targetSlot = WEAPON_SLOT;
	    	} else if (targetItem.getType().equals("armour")) {
	    		targetSlot = ARMOUR_SLOT;
	    	}
	    	
	    	if (targetSlot != -1) {
		    	if (equipped.slotEmpty(targetSlot) && inventory.hasItem(targetItem.getName())) {
					equipped.add(targetSlot, targetItem);
					inventory.removeItem(targetItem);
					
					// Adjusts the stats
					this.attack += targetItem.getStatBuff("attack");
					this.defense += targetItem.getStatBuff("defense");
					this.health += targetItem.getStatBuff("health");
					
					Main.print(name + " has equipped a " + targetItem.getName() + ".");
				} else {
					Main.print("You can't equip that!");
				}
	    	}
    	} catch (NullPointerException e) {
    		Main.print("That item doesn't exist!");
    	}
    }
    
    public void unequipItem(String item) {
    	try {
    		Item targetItem = Dream.itemList.getItem(item);
        	int targetSlot = -1;
        	
	    	if (targetItem.getType().equals("weapon")) {
	    		targetSlot = WEAPON_SLOT;
	    	} else if (targetItem.getType().equals("armour")) {
	    		targetSlot = ARMOUR_SLOT;
	    	} else {
	    		Main.print("You cannot unequip that!");
	    	}
	    	
	    	if (targetSlot != -1) {
		    	if (equipped.hasItem(targetItem.getName())) {
		    		// Adjusts the stats
					this.attack -= targetItem.getStatBuff("attack");
					this.defense -= targetItem.getStatBuff("defense");
					this.health -= targetItem.getStatBuff("health");
					
					equipped.removeItem(targetItem);
					inventory.addItem(targetItem);
					Main.print("The " + targetItem.getName() + " has been unequipped and moved to your inventory.");
				} else {
					Main.print("That slot is already empty!");
				}
	    	}
    	} catch (NullPointerException e) {
    		Main.print("That item doesn't exist!");
    	}
    }
    
    public void consumeItem(String item) {
    	try {
	    	Item targetItem = Dream.itemList.getItem(item);
	    	
	    	if (inventory.hasItem(targetItem.getName()) && targetItem.getType().equals("consumable")) {
	    		// Adjusts the stats
				this.attack += targetItem.getStatBuff("attack");
				this.defense += targetItem.getStatBuff("defense");
				this.health += targetItem.getStatBuff("health");
	    		
	    		inventory.removeItem(targetItem);
	    		Main.print("The " + item + " has been consumed.");
	    	} else {
	    		Main.print("That item can't be consumed!");
	    	}
    	} catch (NullPointerException e) {
    		Main.print("That item doesn't exist!");
    	}
    }
    
    public String getPlayerName() {
    	return playerName;
    }
}
