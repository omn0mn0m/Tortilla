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
	
	private Inventory inventory = new Inventory(INVENTORY_SIZE);	// Player's inventory
	private Inventory equipped = new Inventory(EQUIPPED_SIZE);		// Player's equip inventory
	private final int WEAPON_SLOT = 0;
	private final int ARMOUR_SLOT = 1;
    
	private Input input = new Input();	// Player's input
    private String playerName;		// Class of the player
	

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
        Dream.print("You wake up in your comfortable bed, and as you sit up you see yourself in the mirror.");
        Dream.print("Who do you see staring back at you? (Type the name)");
        Dream.print("| Nam | Nick | Tori |");
        playerName = input.splitAndGetInput(0);
        Dream.print("Okay, so you are " + playerName);
        
        String classFilePath = Main.rootPath + "Player Name.nam";
        Main.namReader.loadFile(classFilePath);
    	
        Main.namReader.findData(String.valueOf(playerName + "-Health"));
    	health =  Main.namReader.getIntData();
    	
    	 Main.namReader.findData(String.valueOf(playerName + "-Attack"));
    	attack =  Main.namReader.getIntData();
    	
    	 Main.namReader.findData(String.valueOf(playerName + "-Defense"));
    	defense =  Main.namReader.getIntData();
    	
    	 Main.namReader.unloadFile();
    	
    	if (! Main.namReader.isFoundElement()) {
    		Dream.print("That may be someone, but not for this story... Try again!");
            selectName();
    	}
    }

    /**
     * Checks for if the player is dead and ends the game if so.
     */
    public void checkIfAlive() {
    	this.checkHealth();
        if (!alive) {
        	Dream.print("You have died.");
            System.exit(0);
        }
    }

    /**
     * Attacks a specified hostile and determines how much damage should be done.
     * @param hostile
     */
    public void attack(Hostile hostile) {
        if (hostile == null) {
        	Dream.print("You can't attack what's not there...");
        } else {
        	int attackBuff = 0;
            Dream.print("Which attack should you use?");
            String attackType = input.getSimpleInput();
            
            if (Dream.attackList.getAttack(attackType) != null) {
            	if (equipped.checkSlot(WEAPON_SLOT) != null && Dream.attackList.getAttack(attackType).getAttackRequires().equalsIgnoreCase(equipped.checkSlot(WEAPON_SLOT).getName())) {
            		attackBuff = Dream.attackList.getAttack(attackType).getAttackBuff();
            	} else {
            		Dream.print("You attack, but a lack of items makes it not as effective...");
            	}
            }
            
        	Dream.print("You " + attackType + " the " + hostile.getName() + ".");
            hostile.takeDamage(this, attackBuff);
        }
    }
    
    /**
     * Runs the script to reroll the character's class and stats.
     */
    public void rerollCharacter() {
    	Dream.print("Are you sure you wish to reroll your character? This will knock you unconscious...");
        if (input.getSimpleInput().equalsIgnoreCase("yes")) {
            this.selectName();
        } else {
        	Dream.print("You have cancelled your rerolling.");
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
    	Dream.print("Slot " + slot + " has been emptied.");
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
	    		Dream.print("The " + item + " has been removed.");
	    	} else {
	    		Dream.print("That item can't be removed!");
	    	}
    	} catch (NullPointerException e) {
    		Dream.print("That item doesn't exist!");
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
	    		Dream.print("The " + item + " has been added.");
	    	} else {
	    		Dream.print("That item can't be added!");
	    	}
    	} catch (NullPointerException e) {
    		Dream.print("That item doesn't exist!");
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
					
					Dream.print(name + " has equipped a " + targetItem.getName() + ".");
				} else {
					Dream.print("You can't equip that!");
				}
	    	}
    	} catch (NullPointerException e) {
    		Dream.print("That item doesn't exist!");
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
	    		Dream.print("You cannot unequip that!");
	    	}
	    	
	    	if (targetSlot != -1) {
		    	if (equipped.hasItem(targetItem.getName())) {
		    		// Adjusts the stats
					this.attack -= targetItem.getStatBuff("attack");
					this.defense -= targetItem.getStatBuff("defense");
					this.health -= targetItem.getStatBuff("health");
					
					equipped.removeItem(targetItem);
					inventory.addItem(targetItem);
					Dream.print("The " + targetItem.getName() + " has been unequipped and moved to your inventory.");
				} else {
					Dream.print("That slot is already empty!");
				}
	    	}
    	} catch (NullPointerException e) {
    		Dream.print("That item doesn't exist!");
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
	    		Dream.print("The " + item + " has been consumed.");
	    	} else {
	    		Dream.print("That item can't be consumed!");
	    	}
    	} catch (NullPointerException e) {
    		Dream.print("That item doesn't exist!");
    	}
    }
    
    public String getPlayerName() {
    	return playerName;
    }
}
