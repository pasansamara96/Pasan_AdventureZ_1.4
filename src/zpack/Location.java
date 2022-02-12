package zpack;

import java.util.HashMap;
import java.util.Map;

/**
 * Locations or rooms in the text adventure game.
 * <p>
 * Player can move through these locations, which contain items and/or zombies.
 * 
 * @author Pasan Samarakkody
 *
 */
public class Location {
	/**
	 * Name of the {@link Location} object
	 */
	private String locationName;
	/**
	 * Description of the {@link Location} object
	 */
	private String locationDescription;
	/**
	 * Neighboring locations of the {@link Location} object
	 */
	private String[] neighbors;
	/**
	 * Items present in the {@link Location} object
	 */
	private HashMap<String, Item> locationItems = new HashMap<String, Item>();
	/**
	 * Undead present in the {@link Location} object
	 */
	private HashMap<String, Undead> locationUndead = new HashMap<String, Undead>();

	/**
	 * Constructor for the {@link Location} object
	 * @param name name of the location
	 * @param description description of the location
	 * @param neighboring neighbors of the location
	 * @param items items of the game
	 * @param undead undead of the game
	 */
	public Location(String name, String description, String[] neighboring, HashMap<String, Item> items,
			HashMap<String, Undead> undead) {
		this.locationName = name;
		this.locationDescription = description;
		this.neighbors = neighboring;
		setItems(items);
		setUndead(undead);
	}

	/**
	 * Method to get the name of the location
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * Method to get the description of the location
	 */
	public String getLocationDescription() {
		return locationDescription;
	}

	/**
	 * Method to get the undead in the location
	 */
	public HashMap<String, Undead> getLocationUndead() {
		return locationUndead;
	}

	/**
	 * Method to get the items in the location
	 */
	public HashMap<String, Item> getInventory() {
		return locationItems;
	}

	/**
	 * Method to set the undead in the location
	 */
	private void setUndead(HashMap<String, Undead> undead) {
		for (Map.Entry<String, Undead> element : undead.entrySet()) { // Go through all undead
			// If undead location matches this location
			if (element.getValue().getUndeadLocation().equals(locationName)) {
				// Put undead in room
				this.locationUndead.put(element.getKey(), element.getValue());
			}
		}
	}

	/**
	 * Method to set the items in the location
	 */
	public void setItems(HashMap<String, Item> items) {
		for (Map.Entry<String, Item> element : items.entrySet()) { // Go through all items
			// If item location matches this location
			if (element.getValue().getItemLocation().equals(locationName)) {
				// Put item in room
				this.locationItems.put(element.getKey(), element.getValue());
			}
		}
	}

	/**
	 * Method to view the location
	 * <p>
	 * Prints the location description and items, undead in location, if any. Also
	 * provides the available exit directions
	 */
	public void view() {
		System.out.println(locationDescription);
		if (locationItems.isEmpty()) {
			System.out.println("There are no items at the location.");
		} else {
			for (Map.Entry<String, Item> element : locationItems.entrySet()) { // Go through location items
				System.out.println(element.getValue().getItemDescription());
			}
		}
		if (locationUndead.isEmpty()) {
			System.out.println("There are no zombies at the location.");
		} else {
			for (Map.Entry<String, Undead> element : locationUndead.entrySet()) { // Go through location undead
				System.out.println(element.getValue().getUndeadDescription());
			}
		}
		String exits = "You can exit to the ";
		if (!neighbors[0].equals("-")) { // If you can exit to the north
			exits += "north, ";
		}
		if (!neighbors[1].equals("-")) { // If you can exit to the south
			exits += "south, ";
		}
		if (!neighbors[2].equals("-")) { // If you can exit to the east
			exits += "east, ";
		}
		if (!neighbors[3].equals("-")) { // If you can exit to the west
			exits += "west, ";
		}
		exits = exits.substring(0, exits.length() - 2);
		exits += ".";
		System.out.println(exits);
	}

	/**
	 * Method to get the neighbors of the location
	 */
	public String[] getNeighbors() {
		return neighbors;
	}
}
