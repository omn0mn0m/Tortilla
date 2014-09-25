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
	
    private Hero hero;
    private LocationMap locationMap = new LocationMap(ROOMS_TO_WIN + 1, ROOMS_TO_WIN + 1);
    public static Random random = new Random();
    
    
    public Dream() {
    	hostileList = new HostileList();
    	Dream.print("Hostiles list successfully loaded!");
    	itemList = new ItemList();
    	Dream.print("Items list successfully loaded!");
        attackList = new AttackList();	// List of attacks the player can do
    	Dream.print("Attacks list successfully loaded!");
    	hero = new Hero();
    	Dream.print("Player successfully loaded!");
    	Dream.print("Swag out. \n");
    	
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
								print("You can't go that way...");
								break;
		            	}
		                break;
		            case "look":
		            	if (input.isSplitWordTarget(1, "around")) {
		            		if (locationMap.getCurrentLocation() != null) {
		            			locationMap.printAllCurrentLocationInformation();
		            		} else {
		            			Dream.print("There is nothing to see...");
		            		}
		            	} else if (input.isSplitWordTarget(1, "at")) {
							locationMap.printTargetHostileStats(input.getInputWord(2));
						}
		            	break;
		            case "attack":
		            	if (input.getSplitLength() >= 2) {
		            		hero.attack(locationMap.getHostileAtCurrentLocation(input.getInputWord(1)));
		            	} else {
		            		Dream.print("You did not choose anything to attack...");
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
		            			print("That is not something valid to check...");
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
		                print("That is not a valid command");
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
		                print("That is not a valid command");
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
    	hero.selectName();
    }
    
    public void checkForWin() {
    	if (locationMap.getRoomsCleared() == ROOMS_TO_WIN) {
    		print("You walk through into the next room, but there is no more dungeon. You have reached the end. Congradulations!");
            System.exit(0);
    	}
    }
    
    public void quit() {
    	print("Are you sure you want to quit? ");
        if(input.getSimpleInput().equalsIgnoreCase("yes")) {
            System.exit(0);
        } else {
        	print("Resuming game then...");
        }
    }
    
    public void restart() {
    	print("Are you sure you want to restart?");
        if(input.getSimpleInput().equalsIgnoreCase("Yes")) {
            locationMap.resetMap();
            locationMap.resetPlayerLocation(hero);
            this.heroClassSelect();
        }
    }
    
    public void pause() {
    	paused = true;
    	print("The game is now paused.");
    }
    
    public void unpause() {
    	paused = false;
    	print("The game is resuming.");
    }
    
    public boolean isPaused() {
    	return paused;
    }
    
    public void printHelp() {
    	if(input.getSplitLength() == 1) {
			while(Main.fileScanner.hasNextLine()) {
				String fileStr = Main.fileScanner.useDelimiter("[\\r\\n]+").next();
				Dream.print(fileStr);
			}
    	} else {
    		switch(input.getInputWord(1)) {
    			case "go":
    				Dream.print("Syntax: go <direction>");
    				Dream.print("You may go north, south, east, or west.");
    				Dream.print("You are not imaginative enough to even think of going other directions.");
    				break;
    			case "look":
    				Dream.print("Syntax: look <arguments> <object>");
    				Dream.print("You can look around anywhere, but you can only look at objects.");
    				break;
    			case "attack":
    				Dream.print("Syntax: attack <enemy>");
    				Dream.print("Just be sure you're attacking what is actually there!");
    				break;
    			case "quit":
    				Dream.print("Syntax: quit");
    				Dream.print("Quits the game and shouts ''I'm a quitter'' to the cosmos.");
    				break;
    			case "restart":
    				Dream.print("Syntax: restart");
    				Dream.print("Restarts the game. In case it wasn't already clear, this wipes your progress.");
    				break;
    			case "reroll":
    				Dream.print("Syntax: reroll");
    				Dream.print("Resets your character's stats, so you can change them.");
    				break;
    			case "check":
    				Dream.print("Syntax: check <vitals>");
    				Dream.print("You can check your stats, inventory, and equipped.");
    				Dream.print("You tried checking some other stuff a while ago, but you found it too difficult and gave up.");
    				break;
    			case "drop":
    				Dream.print("Syntax: drop <item>");
    				Dream.print("Drops the item that you specify. Be careful what you do with basses.");
    				break;
    			case "take":
    				Dream.print("Syntax: take <item>");
    				Dream.print("Takes an item from the surroundings and places it in your inventory.");
    				break;
    			case "equip":
    				Dream.print("Syntax: equip <item>");
    				Dream.print("Equips the item you specify. Just be sure you actually have the item...");
    				break;
    			case "unequip":
    				Dream.print("Syntax: unequip <item>");
    				Dream.print("Removes the item from your equipment and places it in your inventory.");
    				break;
    			case "consume":
    				Dream.print("Syntax: consume <item>");
    				Dream.print("Consumes an item from your inventory and removes it from your inventory.");
    				break;
    			case "pause":
    				Dream.print("Syntax: pause");
    				Dream.print("Pauses the game, like stopping the world, only possible");
    				break;
    			case "unpause":
    				Dream.print("Syntax: unpause");
    				Dream.print("Unpauses the game, like unstopping the world but...");
    				Dream.print("You know, now that I think about it, this is a really bad analogy");
    				break;
    			case "help":
    				Dream.print("Syntax: help <command>");
    				Dream.print("You ask for help, recieving a list of commands if you do not specify one.");
    				Dream.print("Or you ask for help about a specific command, getting the syntax and purpose of it");
    				break;
    		}
    	}
    }
    
    public static void print(String string) {
    	System.out.println(string);
    }
}
