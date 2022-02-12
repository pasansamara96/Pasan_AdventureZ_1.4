package zpack;

/**
 * A main class containing the main method.
 * <p>
 * Entry point to the application.
 * 
 * @author Pasan Samarakkody
 */
public class Main {
	/**
	 * The main method.
	 * <p>
	 * Instantiates the {@link TextAdventure} class and runs the adventure with
	 * {@link TextAdventure#runAdventure} method.
	 * 
	 */
	public static void main(String[] args) {
		TextAdventure adventure = new TextAdventure();
		adventure.runAdventure();
	}
}
