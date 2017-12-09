package nimGame;

import java.util.List;

public abstract class Player {
	
	
	/**
	 * The name of the Player
	 */
	private String name = "Player";
	
	/**
	 * Empty Constructor.
	 */
	public Player() {
		
	}
	
	/**
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name == null || name == "") {
			this.name = "Human Player";
			return;
		}
		this.name = name;
	}
	
	public abstract int[] move(List<Integer> noOfStone); 
}