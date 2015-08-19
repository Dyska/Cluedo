
public class Solution {
	private RoomCard room;
	private CharacterCard character;
	private WeaponCard weapon;
	
	public Solution(RoomCard room, CharacterCard character, WeaponCard weapon) {
		this.room = room;
		this.character = character;
		this.weapon = weapon;
	}

	public RoomCard getRoom() {
		return room;
	}

	public CharacterCard getCharacter() {
		return character;
	}

	public WeaponCard getWeapon() {
		return weapon;
	}
	
	/**
	 * Returns a Cluedo style representation of the Solution.
	 */
	@Override
	public String toString() {
		return "It was " + character.getName() +" in the " + room.getName() + " with a " + weapon.getName() + "!";
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
		Solution other = (Solution) obj;
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

}
