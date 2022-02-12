/**
 * Package for zombie game
 */
package zpack;

import java.util.HashMap;
import java.util.Map;

/**
 * Player in the zombie text adventure game.
 * <p>
 * Can perform various actions such as look, move, use, attack etc.
 * 
 * @author Pasan Samarakkody
 *
 */
public class Player {
	/**
	 * Name of the {@link Player} object
	 */
	private String name;
	/**
	 * Health of the {@link Player} object
	 */
	private int health;
	/**
	 * Power of the {@link Player} object
	 */
	private int power;
	/**
	 * Stamina of the {@link Player} object
	 */
	private int stamina;
	/**
	 * Score of the {@link Player} object
	 */
	private int score;
	/**
	 * Current location of the {@link Player} object
	 */
	private String currentLocation;
	/**
	 * Items in the inventory of the {@link Player} object
	 */
	private HashMap<String, Item> inventory = new HashMap<String, Item>();
	/**
	 * Weapon currently equipped by the {@link Player} object
	 */
	private Weapon activeWeapon;
	/**
	 * Default value of health and stamina for the {@link Player} object
	 */
	private static final int H = 100;

	/**
	 * Constructor for the {@link Player} object
	 * @param pName name of the player
	 * @param pHealth health of the player
	 * @param pPower power of the player
	 * @param pStamina stamina of the player
	 * @param pScore score of the player
	 * @param location location of the player
	 * @param items items of the game
	 */
	public Player(String pName, int pHealth, int pPower, int pStamina, int pScore, String location,
			HashMap<String, Item> items) {
		this.name = pName;
		this.health = pHealth;
		this.power = pPower;
		this.stamina = pStamina;
		this.score = pScore;
		this.currentLocation = location;
		this.activeWeapon = null;
		setInventory(items);
	}

	/**
	 * Method to set the name of the player
	 */
	public void setName(String newName) {
		this.name = newName;
	}

	/**
	 * Method to get the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to get the health of the player
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Method to get the power of the player
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Method to get the stamina of the player
	 */
	public int getStamina() {
		return stamina;
	}

	/**
	 * Method to get the score of the player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Method to get the location of the player
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Method to set the location of the player
	 */
	public void setCurrentLocation(String currentLocation1) {
		this.currentLocation = currentLocation1;
	}

	/**
	 * Method to get the inventory of the player
	 */
	public HashMap<String, Item> getInventory() {
		return inventory;
	}

	/**
	 * Method to set the inventory of the player
	 */
	public void setInventory(HashMap<String, Item> items) {
		// Referred from
		// https://www.geeksforgeeks.org/traverse-through-a-hashmap-in-java/
		for (Map.Entry<String, Item> element : items.entrySet()) { // Go through all items
			// If item location matches inventory
			if (element.getValue().getItemLocation().equals("inventory")) {
				// Put item in inventory
				this.inventory.put(element.getKey(), element.getValue());
			}
		}
	}

	/**
	 * Method to get the active weapon of the player
	 */
	public Weapon getActiveWeapon() {
		return activeWeapon;
	}

	/**
	 * Method to get the active weapon of the player
	 */
	public void setActiveWeapon(Weapon activeWeapon1) {
		this.activeWeapon = activeWeapon1;
	}

	/**
	 * Method to set the health of the player
	 */
	public void setHealth(int health1) {
		this.health = health1;
	}

	/**
	 * Method to set the power of the player
	 */
	public void setPower(int power1) {
		this.power = power1;
	}

	/**
	 * Method to set the stamina of the player
	 */
	public void setStamina(int stamina1) {
		this.stamina = stamina1;
	}

	/**
	 * Method to set the score of the player
	 */
	public void setScore(int score1) {
		this.score = score1;
	}

	/**
	 * Method to get the inventory of the player
	 */
	public void inventory() {
		if (inventory.isEmpty()) {
			System.out.println("Inventory is empty");
		} else {
			System.out.println("Your inventory consists of:");
			for (Map.Entry<String, Item> element : inventory.entrySet()) {
				System.out.println(element.getKey());
			}
		}
	}

	/**
	 * Method to use certain item in inventory of the Player.
	 * <p>
	 * Using items such as weapons, health items impacts the player stats.
	 * 
	 * @param item The selected item
	 */
	public void use(String item) {
		if (inventory.containsKey(item)) {
			Item tempItem = inventory.get(item);
			if (tempItem instanceof Weapon && (Weapon) tempItem != activeWeapon) {
				if (activeWeapon != null) {
					this.power -= this.activeWeapon.getItemStats();
				}
				this.power += ((Weapon) tempItem).getItemStats();
				this.activeWeapon = (Weapon) tempItem;
				System.out.println("Using the " + item + " as a weapon.");
				System.out.println("Your power is now " + power + ".");
			} else if (tempItem instanceof HealthItem) {
				this.health += ((HealthItem) tempItem).getItemStats();
				if (this.health > H) {
					health = H;
				}
				tempItem.setItemLocation("");
				this.inventory.remove(item);
				System.out.println("Your health is restored to " + this.health + ".");
			} else if (tempItem instanceof StaminaItem) {
				this.stamina += ((StaminaItem) tempItem).getItemStats();
				if (this.stamina > H) {
					stamina = H;
				}
				tempItem.setItemLocation("");
				this.inventory.remove(item);
				System.out.println("Your stamina is restored to " + this.stamina + ".");
			} else {
				System.out.println("Using the " + item + " has no effect.");
			}
		} else {
			System.out.println("Item is not in inventory");
		}
	}

	/**
	 * Method to take certain item in the vicinity of the Player.
	 * <p>
	 * These items are added to the player inventory.
	 * 
	 * @param item      The selected item
	 * @param locations The game locations
	 */
	public void take(String item, HashMap<String, Location> locations) {
		Location tempLocation = locations.get(currentLocation);
		Item tempItem;
		if (tempLocation.getInventory().containsKey(item)) {
			tempItem = tempLocation.getInventory().get(item);
			tempItem.setItemLocation("inventory");
			tempLocation.getInventory().remove(item);
			inventory.put(tempItem.getItemName(), tempItem);
			System.out.println("The " + item + " was successfully added to your inventory. You can use it now.");
		} else {
			System.out.println("Sorry, " + item + " is not in the room.");
		}
	}

	/**
	 * Method to drop certain item in the possesion of the Player.
	 * <p>
	 * These items are removed from the player inventory and added to the location
	 * items.
	 * 
	 * @param item      The selected item
	 * @param locations The game locations
	 */
	public void drop(String item, HashMap<String, Location> locations) {
		Location tempLocation = locations.get(currentLocation);
		Item tempItem;
		if (inventory.containsKey(item)) {
			tempItem = inventory.get(item);
			inventory.remove(item);
			tempItem.setItemLocation(currentLocation);
			tempItem.setItemDescription("The " + item + " lies on the floor");
			tempLocation.getInventory().put(tempItem.getItemName(), tempItem);
			System.out.println("The " + item + " was successfully dropped.");
			if (tempItem instanceof Weapon && (Weapon) tempItem == activeWeapon) {
				this.power -= ((Weapon) tempItem).getItemStats();
				System.out.println("Your power is now " + power + ".");
			}
		} else {
			System.out.println("Sorry, " + item + " is not in your inventory.");
		}
	}

	/**
	 * Method to attack undead hostile to the Player.
	 * <p>
	 * These undead will be damaged according to the player power and number of
	 * undead in vicinity
	 * 
	 * @param undead    The game undead
	 * @param locations The game locations
	 */
	public void attack(HashMap<String, Undead> undead, HashMap<String, Location> locations) {
		if (isEmptyStamina()) {
			System.out.println("You have no stamina to attack.");
		} else {
			Undead u;
			HashMap<String, Undead> tempUndead = locations.get(currentLocation).getLocationUndead();
			int individualDamage = this.power / tempUndead.size();
			// System.out.println(individualDamage+","+power+","+tempUndead.size());
			System.out.println("You attack the undead, delivering " + individualDamage + " damage.");
			loseStamina();
			for (Map.Entry<String, Undead> element : tempUndead.entrySet()) {
				u = element.getValue(); // Reduce Undead health according to damage
				boolean undeadKilled = u.takeDamage(individualDamage);
				damageScoreGain(undeadKilled, individualDamage);
				if (undeadKilled) {
					tempUndead.remove(element.getKey());
				}
			}
		}
	}

	/**
	 * Method to check whether player stamina is empty
	 */
	private boolean isEmptyStamina() {
		return (this.stamina == 0) ? true : false;
	}

	/**
	 * Method to reduce the stamina of the player
	 */
	private void loseStamina() {
		this.stamina -= H / 10;
		this.stamina = (stamina >= 0) ? stamina : 0;
	}

	/**
	 * Method to increase the player score based on damage done
	 */
	private void damageScoreGain(boolean undeadKilled, int individualDamage) {
		if (undeadKilled) {
			this.score += H;
		} else {
			this.score += individualDamage;
		}
		// System.out.println("Your Score is "+this.score+".");
	}

	/**
	 * Method to decrease the player score based on damage suffered
	 */
	private void damageScoreLoss(int remainingHealth, int totalDamage) {
		if (remainingHealth == 0) {
			this.score -= H;
		} else {
			this.score -= totalDamage;
		}
	}

	/**
	 * Method for player to move in given direction
	 * <p>
	 * Player will move to a neighbouring location if available
	 * 
	 * @param direction The selected direction
	 * @param locations The game locations
	 */
	public void move(String direction, HashMap<String, Location> locations) {
		Location tempLocation = locations.get(currentLocation);
		String[] tempNeighbors = tempLocation.getNeighbors();
		boolean moveValid = false;
		if (direction.equals("north")) {
			if (!tempNeighbors[0].equals("-")) {
				currentLocation = tempNeighbors[0];
				moveValid = true;
			} else {
				System.out.println("Sorry, you cannot go north. Try again.");
			}
		} else if (direction.equals("south")) {
			if (!tempNeighbors[1].equals("-")) {
				currentLocation = tempNeighbors[1];
				moveValid = true;
			} else {
				System.out.println("Sorry, you cannot go south. Try again.");
			}
		} else if (direction.equals("east")) {
			if (!tempNeighbors[2].equals("-")) {
				currentLocation = tempNeighbors[2];
				moveValid = true;
			} else {
				System.out.println("Sorry, you cannot go east. Try again.");
			}
		} else if (direction.equals("west")) {
			if (!tempNeighbors[3].equals("-")) {
				currentLocation = tempNeighbors[3];
				moveValid = true;
			} else {
				System.out.println("Sorry, you cannot go west. Try again.");
			}
		} else {
			System.out.println("Sorry, it is not a valid direction. Try again.");
		}
		if (moveValid) {
			System.out.println("You move to the " + direction + ".");
		}
	}

	/**
	 * Method for player to look closely at a certain location
	 * <p>
	 * 
	 * @param locations The game locations
	 */
	public void look(HashMap<String, Location> locations) {
		locations.get(currentLocation).view();
	}

	/**
	 * Method for player to glance at a certain location
	 * <p>
	 * 
	 * @param locations The game locations
	 */
	public String firstlook(HashMap<String, Location> locations) {
		return locations.get(currentLocation).getLocationDescription();
	}

	/**
	 * Method to check whether undead present at current location
	 * <p>
	 * 
	 * @param undead    The game undead
	 * @param locations The game locations
	 */
	public boolean checkUndead(HashMap<String, Undead> undead, HashMap<String, Location> locations) {
		Location tempLocation = locations.get(currentLocation);
		if (!tempLocation.getLocationUndead().isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Method for player to take damage from undead attack
	 * <p>
	 * 
	 * @param undead    The game undead
	 * @param locations The game locations
	 */
	public void takeDamage(HashMap<String, Undead> undead, HashMap<String, Location> locations) {
		// TODO Player health should be reduced according to zombie damage in undead
		// hashmap
		HashMap<String, Undead> tempUndead = locations.get(currentLocation).getLocationUndead();
		String attacker = "the undead";
		int totalDamage = 0;
		for (Map.Entry<String, Undead> element : tempUndead.entrySet()) {
			totalDamage += element.getValue().undeadAttack();
			if (element.getValue() instanceof SmartUndead) {// Check if the undead is a smart undead
				attacker = "smart undead";
				((SmartUndead) element.getValue()).unhide();
				System.out.println(element.getValue().getUndeadDescription());
			}
		}
		this.health -= totalDamage;
		if (health < 0) {
			health = 0;
		}
		damageScoreLoss(health, totalDamage);
		System.out.println("You are attacked by " + attacker + ".");
		System.out.println("Total damage is " + totalDamage + ". You have " + health + " health remaining.");
	}

	/**
	 * Method to check if the player is dead
	 */
	public boolean checkDead() {
		return (health <= 0) ? true : false;
	}

	/**
	 * Method to check if the player is in the safe, final location
	 */
	public boolean checkSafe() {
		return (currentLocation.equals("safe room")) ? true : false;
	}
}
