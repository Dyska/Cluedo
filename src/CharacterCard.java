
public class CharacterCard extends Card {
	private String name;
	private Player owner;
	
	/**
	 * Create a new CharacterCard with a given name.
	 * @param name
	 */
	public CharacterCard(String name) {
		super(name);
		this.name = name;
	}

	/**
	 * Return the name of the Character.
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
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterCard other = (CharacterCard) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}

}
