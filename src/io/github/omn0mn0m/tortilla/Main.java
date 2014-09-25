package io.github.omn0mn0m.tortilla;

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

	// Objects for file loading
	public static NamReader namReader = new NamReader();
	public static String rootPath;
	public static String helpFilename;
	public static File HELP_FILE;
	public static Scanner fileScanner;
	
	// Game objects
	public static Characters characters = new Characters();
	
    public static void main(String[] args) {
    	setupFileLoading();	// Sets up the game for loading game files
    	characters.hero.selectName();	// Selection for the character class
    	
    	if (characters.hero.getPlayerName().equalsIgnoreCase("Tori")) {
    		
    	} else if (characters.hero.getPlayerName().equalsIgnoreCase("Nam")) {
    		
    	} else if (characters.hero.getPlayerName().equalsIgnoreCase("Nick")) {
    		
    	}
    }
    
    public static void setupFileLoading() {
    	rootPath = (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) ? "resources/" 
    			: "storage/emulated/0/AppProjects/Tortilla/resources/";
    	
    	HELP_FILE = new File(rootPath + "help_file.txt");
    	
    	try {
			fileScanner = new Scanner(HELP_FILE);
		} catch (FileNotFoundException e) {
			Dream.print("A file is missing...");
		}
    }
}
