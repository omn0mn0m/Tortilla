package io.github.omn0mn0m.tortilla;

import io.github.omn0mn0m.tortilla.entity.Hero;
import io.github.omn0mn0m.tortilla.list.AttackList;
import io.github.omn0mn0m.tortilla.list.HostileList;
import io.github.omn0mn0m.tortilla.list.ItemList;
import io.github.omn0mn0m.tortilla.location.LocationMap;
import io.github.omn0mn0m.util.Input;

import java.util.NoSuchElementException;
import java.util.Random;

public class Dream {

    private final int ROOMS_TO_WIN = 10;
	
	public static HostileList hostileList;
    public static ItemList itemList;
    public static AttackList attackList;
    
	private Input input = new Input();
	private boolean paused = false;
	private boolean isDreaming = true;
	
    private Hero hero;
    private LocationMap locationMap = new LocationMap(ROOMS_TO_WIN + 1, ROOMS_TO_WIN + 1);
    public static Random random = new Random();
    
    
    public Dream() {
    	hostileList = new HostileList();
    	Main.print("Hostiles list successfully loaded!");
    	itemList = new ItemList();
    	Main.print("Items list successfully loaded!");
        attackList = new AttackList();	// List of attacks the player can do
    	Main.print("Attacks list successfully loaded!");
    	hero = new Hero();
    	Main.print("Player successfully loaded!");
    	Main.print("Swag out. \n");
    	
    	locationMap.generateRoomAtPlayer(0, random.nextInt(itemList.getTotalItems()));
		locationMap.enterCurrentMapLocation(hero);
    }
	
    public void runInputCommand() {
    	try {
	    	if (!paused) {
	    		switch (input.splitAndGetInput(0)) {
		            case "go":
		            	switch (input.getInputWord(1)) {
		            		case "north":
		            			checkForWin();
								locationMap.moveTo(-1, 0, hero);
								break;
							case "east":
								checkForWin();
								locationMap.moveTo(0, 1, hero);
								break;
							case "south":
								checkForWin();
								locationMap.moveTo(1, 0, hero);
								break;
							case "west":
								checkForWin();
								locationMap.moveTo(0, -1, hero);
								break;
							default:
								Main.print("You can't go that way...");
								break;
		            	}
		                break;
		            case "look":
		            	if (input.isSplitWordTarget(1, "around")) {
		            		if (locationMap.getCurrentLocation() != null) {
		            			locationMap.printAllCurrentLocationInformation();
		            		} else {
		            			Main.print("There is nothing to see...");
		            		}
		            	} else if (input.isSplitWordTarget(1, "at")) {
							locationMap.printTargetHostileStats(input.getInputWord(2));
						}
		            	break;
		            case "attack":
		            	if (input.getSplitLength() >= 2) {
		            		hero.attack(locationMap.getHostileAtCurrentLocation(input.getInputWord(1)));
		            	} else {
		            		Main.print("You did not choose anything to attack...");
		            	}
		                break;
		            case "quit":
		                this.quit();
		                break;
		            case "restart":
		                this.restart();
		                break;
		            case "reroll":
		                hero.rerollCharacter();
		                break;
		            case "check":
		            	switch (input.getInputWord(1)) {
		            		case "stats":
		            			hero.printStats();
		            			break;
		            		case "inventory":
		            			hero.checkInventory();
		            			break;
		            		case "equipped":
		            			hero.checkEquipped();
		            			break;
		            		default:
		            			Main.print("That is not something valid to check...");
		            			break;
		            	}
		            	break;
		            case "drop":
		            	hero.removeItem(locationMap.getCurrentLocationItems(), input.getInputWord(1));
		            	break;
		            case "take":
		            	hero.addItem(locationMap.getCurrentLocationItems(), input.getInputWord(1));
		            	break;
		            case "equip":
		            	hero.equipItem(input.getInputWord(1));
		            	break;
		            case "unequip":
		            	hero.unequipItem(input.getInputWord(1));
		            	break;
		            case "consume":
		            	hero.consumeItem(input.getInputWord(1));
		            	break;
		            case "pause":
		            	pause();
		            	break;
		            case "unpause":
		            	unpause();
		            	break;
		            case "help":
		            	printHelp();
		            	break;
		            default:
		                Main.print("That is not a valid command");
		                break;
		        }
	    	} else {
	    		switch (input.splitAndGetInput(0)) {
		            case "quit":
		                this.quit();
		                break;
		            case "restart":
		                this.restart();
		                break;
		            case "reroll":
		                hero.rerollCharacter();
		                break;
		            case "pause":
		            	pause();
		            	break;
		            case "unpause":
		            	unpause();
		            	break;
		            case "help":
		            	printHelp();
		            	break;
		            default:
		                Main.print("That is not a valid command");
		                break;
		        }
	    	}
    	} catch (NoSuchElementException e) {}
    }
    
    public void runGame() {
		for (int i = 0; i < locationMap.getHostilesInCurrentLocation(); i++) {
			if (locationMap.getHostileAtCurrentLocation(i) != null) {
				locationMap.getHostileAtCurrentLocation(i).checkIfAlive(hero);
				locationMap.checkIfHostileDead(i, hero);
				if (locationMap.getHostileAtCurrentLocation(i) != null) {
					hero.takeDamage(locationMap.getHostileAtCurrentLocation(i), 0);
				}
			}
		}
    	hero.checkIfAlive();
    }
    
    public void heroClassSelect() {
    	hero.selectClass();
    }
    
    public void checkForWin() {
    	if (locationMap.getRoomsCleared() == ROOMS_TO_WIN) {
    		Main.print("You walk through into the next room, but there is no more dungeon. You have reached the end. Congradulations!");
    		isDreaming = false;
    		locationMap.resetMap();
            locationMap.resetPlayerLocation(hero);
    	}
    }
    
    public void quit() {
    	Main.print("Are you sure you want to quit? ");
        if(input.getSimpleInput().equalsIgnoreCase("yes")) {
            isDreaming = false;
        } else {
        	Main.print("Resuming game then...");
        }
    }
    
    public void restart() {
    	Main.print("Are you sure you want to restart?");
        if(input.getSimpleInput().equalsIgnoreCase("Yes")) {
            locationMap.resetMap();
            locationMap.resetPlayerLocation(hero);
            this.heroClassSelect();
        }
    }
    
    public void pause() {
    	paused = true;
    	Main.print("The game is now paused.");
    }
    
    public void unpause() {
    	paused = false;
    	Main.print("The game is resuming.");
    }
    
    public boolean isPaused() {
    	return paused;
    }
    
    public void printHelp() {
    	if(input.getSplitLength() == 1) {
			while(Main.fileScanner.hasNextLine()) {
				String fileStr = Main.fileScanner.useDelimiter("[\\r\\n]+").next();
				Main.print(fileStr);
			}
    	} else {
    		switch(input.getInputWord(1)) {
    			case "go":
    				Main.print("Syntax: go <direction>");
    				Main.print("You may go north, south, east, or west.");
    				Main.print("You are not imaginative enough to even think of going other directions.");
    				break;
    			case "look":
    				Main.print("Syntax: look <arguments> <object>");
    				Main.print("You can look around anywhere, but you can only look at objects.");
    				break;
    			case "attack":
    				Main.print("Syntax: attack <enemy>");
    				Main.print("Just be sure you're attacking what is actually there!");
    				break;
    			case "quit":
    				Main.print("Syntax: quit");
    				Main.print("Quits the game and shouts ''I'm a quitter'' to the cosmos.");
    				break;
    			case "restart":
    				Main.print("Syntax: restart");
    				Main.print("Restarts the game. In case it wasn't already clear, this wipes your progress.");
    				break;
    			case "reroll":
    				Main.print("Syntax: reroll");
    				Main.print("Resets your character's stats, so you can change them.");
    				break;
    			case "check":
    				Main.print("Syntax: check <vitals>");
    				Main.print("You can check your stats, inventory, and equipped.");
    				Main.print("You tried checking some other stuff a while ago, but you found it too difficult and gave up.");
    				break;
    			case "drop":
    				Main.print("Syntax: drop <item>");
    				Main.print("Drops the item that you specify. Be careful what you do with basses.");
    				break;
    			case "take":
    				Main.print("Syntax: take <item>");
    				Main.print("Takes an item from the surroundings and places it in your inventory.");
    				break;
    			case "equip":
    				Main.print("Syntax: equip <item>");
    				Main.print("Equips the item you specify. Just be sure you actually have the item...");
    				break;
    			case "unequip":
    				Main.print("Syntax: unequip <item>");
    				Main.print("Removes the item from your equipment and places it in your inventory.");
    				break;
    			case "consume":
    				Main.print("Syntax: consume <item>");
    				Main.print("Consumes an item from your inventory and removes it from your inventory.");
    				break;
    			case "pause":
    				Main.print("Syntax: pause");
    				Main.print("Pauses the game, like stopping the world, only possible");
    				break;
    			case "unpause":
    				Main.print("Syntax: unpause");
    				Main.print("Unpauses the game, like unstopping the world but...");
    				Main.print("You know, now that I think about it, this is a really bad analogy");
    				break;
    			case "help":
    				Main.print("Syntax: help <command>");
    				Main.print("You ask for help, recieving a list of commands if you do not specify one.");
    				Main.print("Or you ask for help about a specific command, getting the syntax and purpose of it");
    				break;
    		}
    	}
    }
    
    public boolean isDreaming() {
    	return isDreaming;
    }
}
