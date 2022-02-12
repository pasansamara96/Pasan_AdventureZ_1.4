package zpack;

/**
 * Undead in the text adventure game.
 * <p>
 * Can attack the {@link Player} and be 'killed' by the {@link Player} object.
 * 
 * @author Pasan Samarakkody
 *
 */
public class Undead {
	/**
	 * Name of the {@link Undead} object
	 */
	private String undeadName;
	/**
	 * Description of the {@link Undead} object
	 */
	private String undeadDescription;
	/**
	 * Location of the {@link Undead} object
	 */
	private String undeadLocation;
	/**
	 * Destructive power of the {@link Undead} object
	 */
	private int undeadPower;
	/**
	 * 'Health' of the {@link Undead} object
	 */
	private int undeadHealth; 
	
	/**
	 * Constructor for intializing Undead attributes
	 * <p>
	 * @param name the name of the {@link Undead} object
	 * @param health the health of the {@link Undead} object
	 * @param description the description of the {@link Undead} object
	 * @param location the location of the {@link Undead} object
	 * @param power the destructive power of the {@link Undead} object
	 */
	public Undead(String name, int health, String description, String location, int power) {
		this.undeadName = name;
		this.undeadDescription = description;
		this.undeadLocation = location;
		this.undeadPower = power;
		this.undeadHealth = health;
		
	}
	
	/**
	 * Method to get the health of the Undead
	 */
	public int getUndeadHealth() {
		return undeadHealth;
	}
	
	/**
	 * Method to get the name of the Undead
	 */
	public String getUndeadName() {
		return undeadName;
	}

	/**
	 * Method to get the attack power of the Undead
	 */
	public int undeadAttack() {
		return getUndeadPower();
	}
	
	/**
	 * Method to get the location of the Undead
	 */
	public String getUndeadLocation() {
		return undeadLocation;
	}
	
	/**
	 * Method to get the power of the Undead
	 */
	public int getUndeadPower() {
		return undeadPower;
	}


	/**
	 * Method to set the location of the Undead
	 */
	public void setUndeadLocation(String undeadLocation1) {
		this.undeadLocation = undeadLocation1;
	}
	/**
	 * Method to set the health of the Undead
	 */
	public void setUndeadHealth(int undeadHealth1) {
		this.undeadHealth = undeadHealth1;
	}
	/**
	 * Method to get the description of the Undead
	 */
	public String getUndeadDescription() {
		return undeadDescription;
	}
	/**
	 * Method to set the description of the Undead
	 */
	public void setUndeadDescription(String undeadDescription1) {
		this.undeadDescription = undeadDescription1;
	}
	
	/**
	 * Method to get the impact to Undead health when attacked by player
	 * @param damage the damage done by the player
	 * @return true if the undead is 'killed'
	 */
	public boolean takeDamage(int damage) {
		
		this.undeadHealth -= damage;
		if(undeadHealth<=0) {
			undeadHealth=0;
			System.out.println("A zombie has been 'killed'.");
			return true;
		}
		return false;
		
	}


}
