
public class Suggestion extends Accusation{
	private WeaponCard weapon;
	private RoomCard room;
	private CharacterCard character;
	
	public Suggestion(WeaponCard weapon, RoomCard room, CharacterCard character) {
		super(weapon, room, character);
		this.weapon = weapon;
		this.room = room;
		this.character = character;
	}
	
	/**
	 * Returns Cluedo style representation of the suggestion.
	 */
	@Override
	public String toString() {
		return "It was " + getCharacter().getName() +" in the " + getRoom().getName() + " with a " + getWeapon().getName() + "!";
	}

	public WeaponCard getWeapon() {
		return this.weapon;
	}

	public RoomCard getRoom() {
		return this.room;
	}

	public CharacterCard getCharacter() {
		return this.character;
	}
	
	/**
	 * Compares a given card to the cards in the suggestion.
	 * If any of the cards in the suggestion match, that card
	 * is returned. If no cards match, return null.
	 * @param card
	 * @return
	 */
	public Card compare(Card card) {
		if (card instanceof CharacterCard) {
			if (card.equals(getCharacter())) {
				return card;
			}
		} else if (card instanceof WeaponCard) {
			if (card.equals(getWeapon())) {
				return card;
			}
		} else if (card instanceof RoomCard) {
			if (card.equals(getRoom())) {
				return card;
			}
		}
		//card matched no card in this suggestion
		return null;
	}
}
