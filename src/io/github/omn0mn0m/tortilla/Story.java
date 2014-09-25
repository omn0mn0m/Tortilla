package io.github.omn0mn0m.tortilla;

public class Story {
	
	Dream dream = new Dream();	// What runs for the game to work

	public Story() {
		
	}
	
	public void runDream() {
		// Runs for the duration of the game
	    while (true) {
			dream.runInputCommand(); // Gets the player input and interprets it
	    	if (!dream.isPaused()) {
	    		dream.runGame();	// Runs non-player controlled elements such as hostile attacks
	    	}
	    }
	}
}
