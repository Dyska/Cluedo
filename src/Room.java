
public class Room extends Location{
	private String name;
	private int numPlayers;
	private WeaponCard weapon;
	private Room secretPassage;
	private Room.Door door;
	private int x;
	private int y;
	
	/**
	 * The constructor should create a new Room object,
	 * with the given name.
	 * @param name
	 */
	public Room(String name) {
		super(name);
		this.name = name;
	}
	
	/**
	 * Returns the name of the room.
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * This method should return a different character based
	 * on its type. The character varies on the room, whether
	 * it is a wall, or whether it is a door. These characters
	 * help build an understandable board.
	 */
	@Override
	public String draw() {
		if (this.name.endsWith("Door")) {
			return "|" + "*";
		}
		switch (this.name) {
		case "Kitchen":
			return "|" + "K";
		case "Ball Room":
			return "|" + "B";
		case "Conservatory":
			return "|" + "C";
		case "Lounge":
			return "|" + "L";
		case "Hall":
			return "|" + "H";
		case "Dining Room":
			return "|" + "D";
		case "Billiard Room":
			return "|" + "P";
		case "Library":
			return "|" + "I";
		case "Study":
			return "|" + "S";
		case "Wall":
			return "|" + "+";
		default:
			return "|" + "_";
		}
	}

	/**
	 * Every room must be entered by a door (or a secret
	 * passageway). If it is entered by a door, we store
	 * this door here. If it is entered by a secret
	 * passageway, then we store a default door to exit
	 * from here.
	 * @param door
	 */
	public void setDoor(Room.Door door) {
		this.door = door;
	}
	
	/**
	 * Returns the door that will be exited from.
	 * @return
	 */
	public Room.Door getDoor() {
		return this.door;
	}
	
	/**
	 * Return the name of the board.
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the number of players in the room currently.
	 * @return
	 */
	public int getPlayers() {
		return this.numPlayers;
	}
	
	/**
	 * Increases the number of players in the room by 1.
	 */
	public void incrementPlayers() {
		this.numPlayers++;
	}
	
	/**
	 * Decreases the number of players in the room by 1.
	 */
	public void decrementPlayers() {
		this.numPlayers--;
	}
	
	/**
	 * Returns the weapon that is in the room. If there is
	 * no weapon, return null.
	 * @return
	 */
	public WeaponCard getWeapon() {
		return this.weapon;
	}
	
	/**
	 * If there is no weapon in the room currently, this
	 * method will set the rooms weapon to the weapon
	 * parameter.
	 * @param weapon
	 */
	public void setWeapon(WeaponCard weapon) {
		if (this.weapon == null) {
			this.weapon = weapon;
		}
		System.out.println("There is already a weapon in the "+toString());
	}
	
	/**
	 * Returns the other room this rooms connects to via
	 * a secret passage. If this room does not connect to
	 * another, null is returned.
	 * @return
	 */
	public Room getSecretPassage() {
		return this.secretPassage;
	}
	
	/**
	 * Add a room to connect to the current room via a
	 * secret passageway.
	 * @param secret
	 */
	public void setSecretPassage(Room secret) {
		this.secretPassage = secret;
	}
	
	/**
	 * A class for the doors that lead to a room.
	 */
	public static class Door extends Room {
		private Room room;
		private int x;
		private int y;
		
		public Door(String name, Room room) {
			super(name);
			this.room = room;
		}
		
		/**
		 * Room the door connects to.
		 * @return
		 */
		public Room getRoom() {
			return this.room;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
		
	}
}
