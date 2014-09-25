package io.github.omn0mn0m.dungeoncrawler;

import io.github.omn0mn0m.util.NamReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the main class for the game. Everything for the game runs from here.
 * @author Nam Tran
 *
 */
public class Main {

	public static NamReader namReader = new NamReader();
	public static String rootPath;
	public static String helpFilename;
	public static File HELP_FILE;
	public static Scanner fileScanner;
	
    public static void main(String[] args) {
    	setupFileLoading();
    	
        Game game = new Game();	// What runs for the game to work
        
        game.heroClassSelect();	// Selection for the character class
        
        // Runs for the duration of the game
        while (true) {
			game.runInputCommand(); // Gets the player input and interprets it
        	if (!game.isPaused()) {
        		game.runGame();	// Runs non-player controlled elements such as hostile attacks
        	}
        }
    }
    
    public static void setupFileLoading() {
    	rootPath = (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) ? "resources/" 
    			: "storage/emulated/0/AppProjects/Tortilla/resources/";
    	
    	HELP_FILE = new File(rootPath + "help_file.txt");
    	
    	try {
			fileScanner = new Scanner(HELP_FILE);
		} catch (FileNotFoundException e) {
			Game.print("A file is missing...");
		}
    }
}
