package zpack;

/**
 * Child class of {@link Undead}, representing smart undead that can hide
 * themselves
 * 
 * @author Pasan Samarakkody
 *
 */
public class SmartUndead extends Undead {
	/**
	 * Constructor for{@link SmartUndead} object.
	 * @param name name of the smart undead
	 * @param health health of the smart undead
	 * @param description description of the smart undead
	 * @param location location of the smart undead
	 * @param power power of the smart undead
	 */
	public SmartUndead(String name, int health, String description, String location, int power) {
		super(name, health, description, location, power);
		hide();
	}

	/**
	 * Hidden description for the smart undead
	 */
	private String hiddenDescription;

	/**
	 * Method to hide the undead description
	 */
	public void hide() {
		this.hiddenDescription = super.getUndeadDescription();
		super.setUndeadDescription("");
	}

	/**
	 * Method to unhide the undead description
	 */
	public void unhide() {
		setUndeadDescription(hiddenDescription);
		this.hiddenDescription = "";
	}
}
