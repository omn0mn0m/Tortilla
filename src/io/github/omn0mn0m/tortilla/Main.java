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

	// version
	public static String version = "0.0.5";
	
	// Objects for file loading
	public static NamReader namReader = new NamReader();
	public static String rootPath;
	public static String helpFilename;
	public static File HELP_FILE;
	public static Scanner fileScanner;
	
	// Game objects
	public static Characters characters = new Characters();
	public static ToriStory toriStory;
	
    public static void main(String[] args) {
    	// Sets up the game for loading game files
    	rootPath = (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) ? "resources/" 
    			: "storage/emulated/0/AppProjects/Tortilla/resources/";
    	
    	HELP_FILE = new File(rootPath + "help_file.txt");
    	
    	try {
			fileScanner = new Scanner(HELP_FILE);
		} catch (FileNotFoundException e) {
			print("A file is missing...");
		}

    	toriStory = new ToriStory();
    	
    	print(" ___                                        ");
    	print("-   ---___-               ,     ,, ,,       ");
    	print("   (' ||                 ||   ' || ||   _   ");
    	print("  ((  ||     /'\\ ,._-_ =||= \\ || ||  < \\, ");
    	print(" ((   ||    || ||  ||    ||  || || ||  /-|| ");
    	print("  (( //     || ||  ||    ||  || || || (( || ");
    	print("    -____-  \\\\,/   \\\\,   \\\\, \\\\ \\\\ \\\\  \\/\\ \n");
    	print("v" + version + "\n");
    	
    	characters.hero.selectName();	// Selection for the character class
    	
    	if (characters.hero.getPlayerName().equalsIgnoreCase("Tori")) {
    		toriStory.runStory();
    	} else if (characters.hero.getPlayerName().equalsIgnoreCase("Nam")) {
    		print("Coming in February... Restart the game.");
    	} else if (characters.hero.getPlayerName().equalsIgnoreCase("Nick")) {
    		print("Coming in March... Restart the game.");
    	}
    }
    
    public static void print(String string) {
    	System.out.println(string);
    }
}
