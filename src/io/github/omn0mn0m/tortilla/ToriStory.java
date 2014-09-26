package io.github.omn0mn0m.tortilla;

public class ToriStory extends Story {
	
	public ToriStory() {
		
	}
	
	public void runStory() {
		Main.print("--------------------------");
		Main.print("Tori's Story: Pre-Season");
		Main.print("--------------------------");
		
		Main.print("Hey Tori, this is Nam's disembodied voice! I'm here to chronicle you, me, and Nick's advetures as Tortilla.");
		Main.print("So let's go back in time to 2012... You are talking to Willow at orchestra when an Asian boy walks up.");
		Main.print("Do you say anything?");
		if (input.getSimpleInput().equalsIgnoreCase("yes")) {
			Main.print("You say, \"hi.\" It doesn't matter much, he thinks you hate him.");
		} else {
			Main.print("You just look at him, which he interprets as despise. He thinks you hate him.");
		}
		Main.characters.nam.setRelationship(-1);
		Main.characters.nam.say("Hey Wirrow, you ready to go to class?");
		Main.characters.willow.say("Yeah sure.");
		Main.print("You watch the two of them walk away as the Asian one awkwardly is holding Willow's hand.");
		
		Main.print("I'm not sure what happened from there until you met Nick, so let's just flash-forward... to the start of second semester!");
		Main.print("At lunch, a tall, white boy walks up to your friend Mg and sits down at your lunch table. The nerve of some people!");
		Main.characters.nick.say("Hi Mg!");
		Main.characters.mg.say("Hi Nick!");
		Main.print("So... guess who wasn't actually at this first meeting so let's make something up! You wait a minute for him to say hi to you.");
		Main.print("He didn't say hi to you, are you offended?");
		if (input.getSimpleInput().equals("yes")) {
			Main.print("You cough somewhat loudly in hopes you get his attention.");
		} else {
			Main.print("You continue sitting there, eating your food. Nom nom nom.");
		}
		Main.characters.nick.say("Wait... I didn't notice you! <3 I'm Captain Doctor Mister Professor Nick Kelly!");
		Main.characters.nick.say("That's kinky.");
		Main.print("How do you respond?");
		Main.characters.hero.say(input.getSimpleInput());
		Main.print("Nick starts laughing uncontrollably, and smiles. D'awww you two are getting along!");
		
		Main.print("And thus you have been introduced all the characters: you, Nam, and Nick.");
		Main.print("Nick will continue talking and becoming smitten by your charms, while Nam doesn't exist until June... which we have now arrived at!");
		Main.print("Nam is walking up to you and Nick, then sits down next to you.");
		Main.characters.nam.say("Nick guess who's single...");
		Main.characters.nick.say("Noooooooooooooooooooooooooooooooooooooooooooooooooo");
		Main.print("How do you respond?");
		Main.characters.hero.say(input.getSimpleInput());
		Main.print("Nam smiles, and he is warming up to you!");
		Main.characters.nam.setRelationship(1);
		
		Main.print("A few days later, on June 11, 2013, you are sitting at home on Facebook. All of a sudden you see a message pop up.");
		Main.characters.nam.say("WE NEED TO BAKE HAPPINESS AND RANDOMLY DANCE AND STUFF");
		Main.print("How do you respond? Yes you were the one to respond first, let's see if you remember what you said :P");
		String firstFBMessage = input.getSimpleInput();
		Main.print(firstFBMessage);
		if (firstFBMessage.equalsIgnoreCase("YES WE DO")) {
			Main.print("Wow I didn't think you would remember...");
		} else {
			Main.print("Not quite, but that works too!");
		}
		
		Main.print("And so began Tortilla... Well actually it was called \"EPIC BAKING PLANS\".");
		Main.print("This was a very rough alpha version, so expect a much more game-like experience to replace these past few paragraphs...");
		Main.print("Do you want to go to sleep?");
		
		boolean notSleeping = true;
		while (notSleeping) {
			if (input.getSimpleInput().equals("yes")) {
				Main.print("You plop into bed in your crab sweater and fall asleep.");
				notSleeping = false;
			} else {
				Main.print("There is literally nothing else to do but sleep.");
			}
		}
		
		runDream();
	}
}
