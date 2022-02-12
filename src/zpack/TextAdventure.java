package zpack;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Text adventure game class that takes user input and gives responses.
 * 
 * @author Pasan Samarakkody
 *
 */
public class TextAdventure {
	/**
	 * Tracks whether game is over for player
	 */
	private boolean gameOver;
	/**
	 * Tracks whether saved game available to load
	 */
	private boolean savedGameAvailable;
	/**
	 * Tracks whether user wants to load saved game
	 */
	private boolean loadGame;

	/**
	 * Constructor for {@link TextAdventure}
	 */
	public TextAdventure() {
		this.gameOver = false;
	}

	/**
	 * This method gives the user opportunity to select play new game or previous
	 * saved game, if available.
	 * 
	 * @param userIn The user input scanner
	 */
	public void startAdventure(Scanner userIn) {
		System.out.println(
				"------------------------------------------------------------------------------------------\n************************************ADVENTURE Z*******************************************\n------------------------------------------------------------------------------------------");
		while (savedGameAvailable) {
			System.out.println(
					"# Start New Game : 'start'                                       # Load Saved Game: 'load'");
			String selec = userIn.nextLine().trim().toLowerCase();
			if (selec.equals("start")) {
				this.loadGame = false;
				break;
			} else if (selec.equals("load")) {
				this.loadGame = true;
				break;
			} else {
				System.out.println("Please enter a valid input.");
			}
		}
	}

	/**
	 * Method that runs text adventure, takes user input and gives responses
	 * according to game logic.
	 * 
	 */
	public void runAdventure() {
		// Initiating the AdventureManager class, which handles file loading and saving
		AdventureManager advManager = new AdventureManager();
		this.savedGameAvailable = advManager.isSavedGameAvailable();
		Scanner userIn = new Scanner(System.in);
		startAdventure(userIn);
		// Inspired by zombie game The Last Stand: Union City,
		// https://www.kongregate.com/games/ConArtists/the-last-stand-union-city
		System.out.println(
				"It is Day 68 AZ. Just over two months from the first contact, the first glimpse into our doom.\nA tourist in China was bitten by an infected bat, and it has spread to most of the world.\nZombies have taken over and civilization is on its last legs.");
		System.out
				.println("------------------------------------------------------------------------------------------");
		// Initiate the game objects in HashMap and initialize the player, Reference:
		// https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html
		HashMap<String, Item> items = advManager.createItems(loadGame);
		HashMap<String, Undead> undead = advManager.createUndead(loadGame);
		HashMap<String, Location> locations = advManager.createLocations(items, undead);
		Player player = advManager.createPlayer(loadGame, items);
		boolean reactionTime = (loadGame) ? true : false;
		System.out.println(player.firstlook(locations));
		while (!gameOver) {
			String command = userIn.nextLine();
			command = command.toLowerCase();
			String[] commandWords = command.split("\\s+"); // Count words to filter out lengthy commands, combines all
															// whitespace as delimiter
			// Check if there are any undead near player and they have enough reaction time
			// to attack player
			if (reactionTime && player.checkUndead(undead, locations)) {
				player.takeDamage(undead, locations);
			}
			// Check if the player is dead
			if (player.checkDead()) {
				gameOver = true;
				System.out.println("Unfortunately, you seem to have died. C'est la vie!");
				System.out.println("Your score is " + player.getScore() + ".");
				continue;
			}
			// For commands exceeding two words, ask to rephrase
			if (commandWords.length > 2) {
				System.out.println("I find it difficult to understand. Can you put it simply?");
			} else if (commandWords[0].equals("look")) {
				player.look(locations);
			} else if (commandWords[0].equals("attack")) {
				if (player.checkUndead(undead, locations)) {
					player.attack(undead, locations);
				} else {
					System.out.println("There is no threat in the room to attack.");
				}
			} else if (commandWords[0].equals("save")) {
				advManager.save(player, undead, items);
			} else if (commandWords.length == 1) {
				if (commandWords[0].equals("inventory")) {
					player.inventory();
				} else if (commandWords[0].equals("scream") || commandWords[0].equals("shout")) {
					System.out.println("Please calm down. This is not helping your situation.");
				} else if (commandWords[0].equals("quit")) {
					gameOver = true;
					System.out.println("You have decided to quit. Zombies will kill you shortly.");
					System.out.println("Score is " + player.getScore() + ".");
				}
				// For empty commands, ask to type a command
				else if (commandWords[0].equals("")) {
					System.out.println("Please type a command.");
				} else {
					System.out.println("That does not make sense.");
				}
			}
			// For checking player stats
			else if (commandWords[0].equals("check")) {
				switch (commandWords[1]) {
				case "score":
					System.out.println("Your score is " + player.getScore() + ".");
					break;
				case "health":
					System.out.println("Your health is " + player.getHealth() + ".");
					break;
				case "stamina":
					System.out.println("Your stamina is " + player.getStamina() + ".");
					break;
				case "power":
					System.out.println("Your power is " + player.getPower() + ".");
					break;
				}
			} else if (commandWords[0].equals("use")) {
				player.use(commandWords[1]);
			} else if (commandWords[0].equals("take")) {
				String item = commandWords[1];
				player.take(item, locations);
			} else if (commandWords[0].equals("drop")) {
				String item = commandWords[1];
				player.drop(item, locations);
			} else if (commandWords[0].equals("go") || commandWords[0].equals("exit")
					|| commandWords[0].equals("move")) {
				String nextLocation = commandWords[1];
				player.move(nextLocation, locations);
				// Check if the player reached the final safe location
				if (player.checkSafe()) {
					gameOver = true;
					System.out.println(player.firstlook(locations));
					System.out.println("Congratulations on surviving! Your score is " + player.getScore() + ".");
					continue;
				}
				player.look(locations);
				reactionTime = true;
			} else {
				System.out.println("That makes no sense.");
			}
			reactionTime = (player.checkUndead(undead, locations)) ? true : false;
		}
		userIn.close();
	}
}
