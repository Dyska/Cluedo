
public class Location {

	private int x;
	private int y;
	private Player player;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Location(String name) {
		//alternative constructor for Room
	}
	
	
	/**
	 * Return the way to draw the location. If the square
	 * has a player on it, and they are in the game, then
	 * return their player number. Otherwise, just return
	 * a default value.
	 * @return
	 */
	public String draw(){
		if (hasPlayer()) {
			if (player.getInGame()) {
				return("|" + player.getPlayerNumber());
			}
		}
		return ("|" + "_");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * This method should add a player to a location.
	 * @param newPlayer
	 * @return
	 */
	public void addPlayer(Player newPlayer) {
			this.player = newPlayer;
	}
	
	/**
	 * This method should remove the player from the current location.
	 * @return
	 */
	public Location removePlayer() {
		this.player = null;
		return this;
	}
	
	/**
	 * This method should return true if the location has a player,
	 * otherwise false.
	 * @return
	 */
	public boolean hasPlayer() {
		return player != null;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Return the location in a string format.
	 */
	@Override
	public String toString() {
		return "("+this.x+", "+this.y+")";
	}
}
