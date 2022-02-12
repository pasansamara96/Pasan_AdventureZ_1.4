package zpack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles game data from database.
 * <p>
 * Contain methods to create and save the {@link Player}, {@link Location},
 * {@link Item} and {@link Undead} objects from/to the data files.
 * 
 */
public class AdventureManager {
	/**
	 * Method that loads Item data.
	 * <p>
	 * Creates the {@link Item} objects from the data files.
	 * <p>
	 * Reference:
	 * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
	 * 
	 * @param loadGame True if user selected to load saved game.
	 */
	public HashMap<String, Item> createItems(boolean loadGame) {
		try {
			String fileLocation = (!loadGame) ? "start/Items.txt" : "saved/Items.txt";
			// String fileLocation = loadGame + "Items.txt";
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
			String line = reader.readLine();
			HashMap<String, Item> items = new HashMap<String, Item>();
			while (line != null) { // While there are remaining lines in file
				String name = line;
				name = name.toLowerCase();
				line = reader.readLine();
				String type = line.trim();
				line = reader.readLine();
				int stats = Integer.parseInt(line.trim());
				line = reader.readLine();
				String location = line.trim();
				line = reader.readLine();
				String description = "";
				while (!line.equals("END")) { // While "END" is not encountered
					description = description + line + '\n'; // Add multiple lines to description
					line = reader.readLine();
				}
				description = description.substring(0, description.length() - 1);
				// System.out.println(description);
				// Add new items into HashMap
				switch (type) {
				case "weapon":
					items.put(name, new Weapon(name, stats, description, location));
					break;
				case "staminaitem":
					items.put(name, new StaminaItem(name, stats, description, location));
					break;
				case "healthitem":
					items.put(name, new HealthItem(name, stats, description, location));
					break;
				default:
					items.put(name, new Item(name, description, location));
				}
				line = reader.readLine(); // Move to next section
			}
			reader.close();
			return items;
		} catch (IOException e) {
			System.out.println("File could not be accessed, try again!");
		}
		return null;
	}

	/**
	 * Method that loads Undead data.
	 * <p>
	 * Creates the {@link Undead} objects from the data files.
	 * 
	 * @param loadGame True if user selected to load saved game.
	 */
	public HashMap<String, Undead> createUndead(boolean loadGame) {
		try {
			String fileLocation = (!loadGame) ? "start/Undead.txt" : "saved/Undead.txt";
//			String fileLocation = loadGame + "Undead.txt";
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
			String line = reader.readLine();
			HashMap<String, Undead> undead = new HashMap<String, Undead>();
			while (line != null) { // While there are remaining lines
				String name = line;
				name = name.toLowerCase();
				line = reader.readLine();
				String type = line.trim();
				// System.out.println("-"+type+"-");
				line = reader.readLine();
				int health = Integer.parseInt(line.trim());
				line = reader.readLine();
				String location = line.trim();
				line = reader.readLine();
				int power = Integer.parseInt(line.trim());
				line = reader.readLine();
				String description = "";
				while (!line.equals("END")) { // While "END" is not encountered
					description = description + line + '\n'; // Add multiple lines to description
					line = reader.readLine();
				}
				description = description.substring(0, description.length() - 1);
				// Add new undead into HashMap
				// undead.put(name, new Undead(name, description, location, power));
				if (type.equals("smartundead") && health > 0) {
					undead.put(name, new SmartUndead(name, health, description, location, power));
				} else if (type.equals("undead") && health > 0) {
					undead.put(name, new Undead(name, health, description, location, power));
				}
				line = reader.readLine(); // Move to next section
			}
			reader.close();
			return undead;
		} catch (IOException e) {
			System.out.println("File could not be accessed, try again!");
		}
		return null;
	}

	/**
	 * Method that loads Location data.
	 * <p>
	 * Creates the {@link Location} objects from the data files.
	 * 
	 * @param items  HashMap containing game items
	 * @param undead HashMap containing game undead
	 */
	public HashMap<String, Location> createLocations(HashMap<String, Item> items, HashMap<String, Undead> undead) {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader("start/Locations.txt"));
			String line = fileReader.readLine();
			HashMap<String, Location> locations = new HashMap<String, Location>();
			while (line != null) { // While there are remaining lines in file
				String name = line;
				// System.out.println(name);
				line = fileReader.readLine(); // Read the next line
				String[] neighbors = line.split(",");
				for (int i = 0; i < neighbors.length; i++) {
					neighbors[i] = neighbors[i].trim();
				}
				line = fileReader.readLine();
				String description = "";
				while (!line.equals("END")) { // While "END" is not encountered
					description = description + line + '\n'; // Add multiple lines to description
					line = fileReader.readLine();
				}
				description = description.substring(0, description.length() - 1);
				// Add new locations into HashMap
				locations.put(name, new Location(name, description, neighbors, items, undead));
				line = fileReader.readLine();
			}
			fileReader.close();
			return locations;
		} catch (IOException e) { // Handle filenotfound exception
			System.out.println("File could not be accessed, try again!");
		}
		return null;
	}

	/**
	 * Method that loads Player data.
	 * <p>
	 * Creates the {@link Player} object from the data file.
	 * 
	 * @param loadGame True if user has selected to load saved game
	 * @param items    HashMap containing game items
	 */
	public Player createPlayer(boolean loadGame, HashMap<String, Item> items) {
		try {
			String fileLocation = (!loadGame) ? "start/Player.txt" : "saved/Player.txt";
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
			String line = reader.readLine();
			Player p = null;
			while (line != null) { // While there are remaining lines
				String name = line;
				name = name.toLowerCase();
				line = reader.readLine();
				int health = Integer.parseInt(line.trim());
				line = reader.readLine();
				int power = Integer.parseInt(line.trim());
				line = reader.readLine();
				int stamina = Integer.parseInt(line.trim());
				line = reader.readLine();
				int score = Integer.parseInt(line.trim());
				line = reader.readLine();
				String location = line.trim();
				p = new Player(name, health, power, stamina, score, location, items);
				line = reader.readLine(); // Move to next section
			}
			reader.close();
			// if (loadGame) {createInventory();}
			return p;
		} catch (IOException e) {
			System.out.println("File could not be accessed, try again!");
		}
		return null;
	}

	/**
	 * Method that saves game object data.
	 * <p>
	 * Works by invoking individual methods for each class type
	 * 
	 * @param player Current player object
	 * @param undead Current game undead as HashMap
	 * @param items  Current game items as HashMap
	 */
	public void save(Player player, HashMap<String, Undead> undead, HashMap<String, Item> items) {
		savePlayer(player);
		saveUndead(undead);
		saveItems(items);
		System.out.println("Game status has been saved.");
	}

	/**
	 * Method that saves Item data.
	 * <p>
	 * Writes the {@link Item} object attributes to the data file.
	 * <p>
	 * Reference:
	 * https://www.programcreek.com/2011/03/java-write-to-a-file-code-example/
	 * 
	 * @param items HashMap containing game items
	 * 
	 */
	private void saveItems(HashMap<String, Item> items) {
		try {
			File fileOut = new File("saved/Items.txt");
			FileOutputStream fileOutStr = new FileOutputStream(fileOut);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(fileOutStr));
			for (Map.Entry<String, Item> element : items.entrySet()) {
				Item fileItem = element.getValue();
				bWriter.write(fileItem.getItemName());
				bWriter.newLine();
				bWriter.write(fileItem.getClass().getSimpleName().toLowerCase());
				bWriter.newLine();
				bWriter.write(fileItem.getItemStats() + "");
				bWriter.newLine();
				bWriter.write(fileItem.getItemLocation());
				bWriter.newLine();
				bWriter.write(fileItem.getItemDescription());
				bWriter.newLine();
				bWriter.write("END");
				bWriter.newLine();
			}
			// bWriter.newLine();
			bWriter.close();
		} catch (IOException e) {
			System.out.println("File could not be accessed, try again!");
		}
	}

	/**
	 * Method that saves Undead data.
	 * <p>
	 * Writes the {@link Undead} object attributes to the data file.
	 * 
	 * @param undead HashMap containing game undead
	 */
	private void saveUndead(HashMap<String, Undead> undead) {
		try {
			File fileOut = new File("saved/Undead.txt");
			FileOutputStream fileOutStr = new FileOutputStream(fileOut);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(fileOutStr));
			for (Map.Entry<String, Undead> element : undead.entrySet()) {
				Undead fileUndead = element.getValue();
				if (fileUndead instanceof SmartUndead) {
					((SmartUndead) fileUndead).unhide();
				}
				bWriter.write(fileUndead.getUndeadName());
				bWriter.newLine();
				bWriter.write(fileUndead.getClass().getSimpleName().toLowerCase());
				bWriter.newLine();
				bWriter.write(fileUndead.getUndeadHealth() + "");
				bWriter.newLine();
				bWriter.write(fileUndead.getUndeadLocation());
				bWriter.newLine();
				bWriter.write(fileUndead.getUndeadPower() + "");
				bWriter.newLine();
				bWriter.write(fileUndead.getUndeadDescription());
				bWriter.newLine();
				bWriter.write("END");
				bWriter.newLine();
			}
			// bWriter.newLine();
			bWriter.close();
		} catch (IOException e) {
			System.out.println("File could not be accessed, try again!");
		}
	}

	/**
	 * Method that saves Player data.
	 * <p>
	 * Writes the {@link Player} object attributes to the data file.
	 * 
	 * @param player Current player object
	 */
	private void savePlayer(Player player) {
		try {
			File fileOut = new File("saved/Player.txt");
			FileOutputStream fileOutStr = new FileOutputStream(fileOut);
			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(fileOutStr));
			bWriter.write(player.getName());
			bWriter.newLine();
			bWriter.write(player.getHealth() + "");
			bWriter.newLine();
			bWriter.write(player.getPower() + "");
			bWriter.newLine();
			bWriter.write(player.getStamina() + "");
			bWriter.newLine();
			bWriter.write(player.getScore() + "");
			bWriter.newLine();
			bWriter.write(player.getCurrentLocation());
			bWriter.close();
		} catch (IOException e) {
			System.out.println("File could not be accessed, try again!");
		}
	}

	/**
	 * Method that checks whether a saved game is available.
	 * <p>
	 * Checks the '/saved' directory for the files.
	 * 
	 * @return true if the game is available
	 */
	public boolean isSavedGameAvailable() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("saved/Player.txt"));
			reader.close();
			return true;
		} catch (IOException e) {
			// System.out.println("File could not be accessed, try again!");
			return false;
		}
	}
}
