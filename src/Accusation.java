
public class Accusation {
	private WeaponCard weapon;
	private RoomCard room;
	private CharacterCard character;
	
	/**
	 * To make an accusation, a player must specify a weapon,
	 * a room, and a character.
	 * @param weapon
	 * @param room
	 * @param character
	 */
	public Accusation(WeaponCard weapon, RoomCard room, CharacterCard character) {
		this.weapon = weapon;
		this.room = room;
		this.character = character;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((character == null) ? 0 : character.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((weapon == null) ? 0 : weapon.hashCode());
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
		Accusation other = (Accusation) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (weapon == null) {
			if (other.weapon != null)
				return false;
		} else if (!weapon.equals(other.weapon))
			return false;
		return true;
	}
	
	/**
	 * This is a special equals method to compare an Accusation to
	 * a Solution. Each of the 3 cards is compared, and only if
	 * all 3 of the cards are the same, true is returned.
	 * @param solution
	 * @return
	 */
	public boolean equals(Solution solution) {
		boolean equal = true;
		equal = equal && solution.getCharacter().equals(getCharacter()); //true if characters are equal
		equal = equal && solution.getRoom().equals(getRoom()); //true if rooms are equal
		equal = equal && solution.getWeapon().equals(getWeapon()); //true if weapons are equal
		return equal; //true if all 3 are equal
	}
	
	@Override
	public String toString() {
		return "It was " + getCharacter().getName() +" in the " + getRoom().getName() + " with a " + getWeapon().getName() + "!";
	}

	/**
	 * Returns the weapon of the accusation.
	 * @return
	 */
	public WeaponCard getWeapon() {
		return this.weapon;
	}

	/**
	 * Returns the room in the accusation.
	 * @return
	 */
	public RoomCard getRoom() {
		return this.room;
	}

	/**
	 * Returns the character who is being accused.
	 * @return
	 */
	public CharacterCard getCharacter() {
		return this.character;
	}
}
