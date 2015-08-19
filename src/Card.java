
public class Card {
	private String name;
	private Player owner;

	/**
	 * Create a new Card, with a given name.
	 * @param name
	 */
	public Card(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public Player getPlayer() {
		return this.owner;
	}

	/**
	 * Return the name of the Card.
	 */
	@Override
	public String toString() {
		return name;
	}

	public void setPlayer(Player player) {
		this.owner = player;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
