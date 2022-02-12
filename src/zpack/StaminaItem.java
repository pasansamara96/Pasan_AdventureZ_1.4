package zpack;

/**
 * Child class of {@link Item} to represent items that replenish player stamina
 * 
 * @author Pasan Samarakkody
 *
 */
public class StaminaItem extends Item {
	/**
	 * Stamina given by the item
	 */
	private int staminaGiven;

	/**
	 * Constructor for {@link StaminaItem} object
	 * @param name name of the item
	 * @param stats stamina given by the item
	 * @param description description of the item
	 * @param location location of the item
	 */
	public StaminaItem(String name, int stats, String description, String location) {
		super(name, description, location);
		this.staminaGiven = stats;
	}

	/**
	 * Method to get the stamina given
	 */
	public int getItemStats() {
		return staminaGiven;
	}
}
