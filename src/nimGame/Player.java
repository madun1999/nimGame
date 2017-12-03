package nimGame;

public class Player {
	

	public Player() {
		
	}
	/**
	 * @param isAI
	 * @param name
	 */
	public Player(boolean isAI, String name) {
		super();
		this.isAI = isAI;
		this.name = name;
	}
	
	/**
	 * if the player is an AI
	 */
	private boolean isAI = false;
	
	/**
	 * The name of the Player
	 */
	private String name = "Player";
	
	/**
	 * @return the isAI
	 */
	public boolean isAI() {
		return isAI;
	}
	/**
	 * @param isAI the isAI to set
	 */
	public void setAI(boolean isAI) {
		this.isAI = isAI;
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
			if (isAI) {
				this.name = "AI Player";
			} else {
				this.name = "Human Player";
			}
		}
		this.name = name;
	}
	
}
