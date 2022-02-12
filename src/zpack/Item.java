package zpack;

/**
 * Items in the text adventure game.
 * Can be picked up, dropped and used by player.
 * 
 * @author Pasan Samarakkody
 *
 */
public class Item {
	/**
	 * Name of the {@link Item} object
	 */
	private String itemName;  
	/**
	 * Description of the {@link Item} object
	 */
	private String itemDescription;
	/**
	 * Location of the {@link Item} object
	 */
	private String itemLocation;

	/**
	 * Constructor for the {@link Item} object
	 * @param name name of the item
	 * @param description description of the item
	 * @param location location of the item
	 */
	public Item(String name, String description, String location) {
		this.itemName = name;
		this.itemDescription = description;
		this.itemLocation = location;
	}
	
	/**
	 * Method to get the name of the item
	 */
	public String getItemName() {
		return itemName;
		}
	/**
	 * Method to get the description of the item
	 */
	public String getItemDescription() {
		return itemDescription;
		}
	/**
	 * Method to set the description of the item
	 */
	public void setItemDescription(String itemDescription1) {
		this.itemDescription = itemDescription1;
	}
	/**
	 * Method to get the location of the item
	 */
	public String getItemLocation() {
		return itemLocation;
	}
	/**
	 * Method to set the location of the item
	 */
	public void setItemLocation(String itemLocation1) {
		this.itemLocation = itemLocation1;
	}
	/**
	 * Method to get the stats of the item
	 */
	public int getItemStats() {
		return 0;
	}

	

}
