package io.github.omn0mn0m.tortilla;

import io.github.omn0mn0m.util.Input;

public class Story {
	
	Dream dream;	// What runs for the dream mode
	Input input = new Input(); // Player input

	public Story() {
		
	}
	
	public void runDream() {
		dream = new Dream();
		dream.heroClassSelect();
		// Runs for the duration of the game
	    while (dream.isDreaming()) {
			dream.runInputCommand(); // Gets the player input and interprets it
	    	if (!dream.isPaused()) {
	    		dream.runGame();	// Runs non-player controlled elements such as hostile attacks
	    	}
	    }
	}
}
