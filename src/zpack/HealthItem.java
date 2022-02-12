package zpack;

/**
 * Child class of {@link Item} to represent items that replenish player health
 * @author Pasan Samarakkody
 *
 */
public class HealthItem extends Item {
	/**
	 * Health given by the item
	 */
	private int healthGiven;
	/**
	 * Constructor for {@link HealthItem} object
	 * @param name name of the item
	 * @param stats health given by the item
	 * @param description description of the item
	 * @param location location of the item
	 */
	public HealthItem (String name, int stats, String description, String location) {
		super(name, description, location);
		this.healthGiven = stats;
	}
	/**
	 * Method to get the health given
	 */
	public int getItemStats() {return healthGiven;}
	

}
