package zpack;

/**
 * Child class of {@link Item} to represent items that can be used as a weapon
 * @author Pasan Samarakkody
 *
 */
public class Weapon extends Item {
	/**
	 * Destructive power of the item
	 */
	private int weaponPower;
	/**
	 * Constructor for {@link Weapon} object
	 * @param name name of the item
	 * @param stats power of the item
	 * @param description description of the item
	 * @param location location of the item
	 */
	public Weapon(String name, int stats, String description, String location) {
		super(name, description, location);
		this.weaponPower = stats;
	}

	/**
	 * Method to get the power of the weapon
	 */
	public int getItemStats() {return weaponPower;}
	
}
