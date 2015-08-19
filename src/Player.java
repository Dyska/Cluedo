import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Player{
	private ArrayList<Card> hand;
	private boolean inGame;
	private CharacterCard character;
	private ArrayList<Card> allCards;
	private Location location;
	private int playerNumber;
	private Board board;
	private Room prevRoom;
	private Game game;

	public Player(int playerNumber, CharacterCard character) {
		this.character = character;
		this.playerNumber = playerNumber;
		this.hand =  new ArrayList<Card>();
		this.inGame = true; //begin by being in the game
	}
	
	/**
	 * 'Deal' the player a card. 
	 * @param card to be added to users hand.
	 */
	public void dealCard(Card card) {
		this.hand.add(card);
	}
	
	/**
	 * Two 'dice' are rolled using random number generator.
	 * The sum of these two numbers is returned.
	 * @return
	 */
	public int rollDice() {
		int dice1 = (int) Math.round(Math.random()*6);
		int dice2 = (int) Math.round(Math.random()*6);
		
		if (dice1 == 0) {dice1++;}
		if (dice1 == 1) {dice2++;}
		int sum = dice1 + dice2;
		return sum;
	}
	
	/**
	 * This method is the master method for a player taking their turn.
	 * The player should be able to roll dice, move around the board, 
	 * leave and enter rooms, take secret passage ways and make suggestions
	 * and accusations.
	 * @param leavingRoom true if user has just left a room.
	 */
	public void takeTurn(boolean leavingRoom) {
		boolean startInRoom = getLocation() instanceof Room;
				
		if (!leavingRoom) {
			//if player is just starting their turn, come in here
			System.out.println("Player "+playerNumber+", it is your turn.");
		}
		if (checkForPassageways()) {
			//player has decided to go down the available secret passageway
			//can now make accusation or suggestion
			return;	
		}
		int steps = rollDice();
		System.out.println();
		if (!startInRoom) {
			System.out.println("Player "+ playerNumber+" rolled a "+steps+".");
		}
		
		while (steps > 0 && (!(this.location instanceof Room)
				|| this.location instanceof Room.Door)) { //still have steps, and aren't in a room yet
			if (steps != 1) {
				System.out.println("Player "+playerNumber+ " has "+steps+""
						+ " more squares to move this turn.");
			} else {
				System.out.println("Player "+playerNumber+" has"
						+ " 1 more step to move this turn."); 
			}
			move();
			steps--; 
			this.board.displayBoard();
		}
		if (getLocation() instanceof Room && startInRoom && ((Room) getLocation()).getName().equals(getPrevRoom().getName())) {
			//starting out in room, should decide whether to stay in this room or not
			Room room = (Room) getLocation();
			System.out.println("Player " + getPlayerNumber()+" is in the "+(((Room) getLocation())).toString() + ".");
			String input = inputString("Exit the room? (Y, N)");
			if (input.toUpperCase().equals("Y")) {
				//moving back on the board
				setPrevRoom((Room) getLocation());
				setLocation(this.board.getBoard()[room.getDoor().getX()][room.getDoor().getY()]);
				takeTurn(true);
			} else {
				askForSuggestion();
			}
		}
		if (getLocation() instanceof Room && getPrevRoom() == null) {
			//haven't been in a room before
			System.out.println("Player " + playerNumber+ 
					" has entered the " +((Room) getLocation()).toString() + ".");
			setPrevRoom((Room) getLocation());
			askForSuggestion();
		} else if (getLocation() instanceof Room && !getPrevRoom().getName().equals(((Room) getLocation()).getName())) {
			//have been in another room before
			System.out.println("Player " + playerNumber+ 
					" has entered the " +((Room) getLocation()).toString() + ".");
			setPrevRoom((Room) getLocation());
			askForSuggestion();
		}
		askForAccusation();
	}

	/**
	 * This method should check if there is a secret passageway for the
	 * player to take. If there is, it should prompt them to take it.
	 * @return true if player takes a passageway, otherwise false
	 */
	private boolean checkForPassageways() {
		if (getLocation() instanceof Room) {
			Room room = (Room) getLocation();
			if (room.getSecretPassage() != null) {
				//there is a secret passageway
				Room secretRoom = room.getSecretPassage();
				System.out.println("There is a secret passageway in the "+getLocation() + "!");
				System.out.println("It leads to the " +secretRoom.getName() +".");
				String input = inputString("Take the passageway? (Y, N)");
				if (input.toUpperCase().equals("Y")) {
					//user wants to take the passageway
					setLocation(secretRoom);
					System.out.println("Player "+playerNumber+ " has entered the "+secretRoom+".");
					askForSuggestion();
					askForAccusation();
					return true;
				} else {
					//don't take the passageway
					System.out.println("Player "+playerNumber+" leaves the room.");
					setPrevRoom(room);
					setLocation(this.board.getBoard()[room.getDoor().getX()][room.getDoor().getY()]);
				}
			}
		}
		//player isn't a room, so they can't take a passageway
		return false;
	}
	
	/**
	 * This method should get input from the user about where on the board
	 * they would like to move. They choose a direction to move in, and if
	 * the direction leads them to a valid square, they are moved to it.
	 * If the direction leads them to an invalid square, they are prompted
	 * to enter a different direction.
	 */
	private void move() {	
		String input = inputString("Please choose a direction (W, A, S, D): ");
		while (!input.equals("W") && !input.equals("A") && !input.equals("S") && !input.equals("D")) {
			input = inputString("Please choose a valid direction (W, A, S, D): ");
		}
		Location old = getLocation().removePlayer(); //remove player from old square
		Location temp = getNewLocation(input,old);
		
		if (isValidMove(temp)) {
			//this is a valid move
			Location newLocation = board.getBoard()[temp.getX()][temp.getY()];
			setLocation(newLocation);
			getLocation().addPlayer(this); //add player to this square
			if (newLocation instanceof Room.Door) {
				//we've found a door leading to a Room
				Room.Door door = (Room.Door) newLocation;
				door.setX(temp.getX()); door.setY(temp.getY());
				Room room = door.getRoom();
				room.setDoor(door); //so we know where to exit from
				setLocation(room);
			}
		} else {
			//invalid move, so the player should try again
			move();
		}
	}
	
	/**
	 * This method should calculate the new location of a player,
	 * given a user input
	 * @param input
	 * @param old
	 * @return
	 */
	private Location getNewLocation(String input, Location old) {
		Location newLocation = null; //new location will be set soon

		if (input.equals("W")) {
			//move player up 1
			newLocation = new Location(old.getX()-1, old.getY());
		} 
		else if (input.equals("A")) {
			//move player left 1
			newLocation = new Location(old.getX(), old.getY()-1);
		} 
		else if (input.equals("D")) {
			//move player right 1 
			newLocation = new Location(old.getX(), old.getY()+1);
		} 
		else if (input.equals("S")) {
			//move player down 1
			newLocation = new Location(old.getX()+1, old.getY());
		}
		return newLocation;
	}

	/**
	 * Return true if the given location is valid, otherwise return
	 * false.
	 * @param newLocation location to be checked
	 * @return
	 */
	private boolean isValidMove(Location newLocation) {
		//can't exceed board limits
		if (newLocation.getX() > 24 || newLocation.getX() < 0 ||
				newLocation.getY() > 24 || newLocation.getY() < 0) {
			System.out.println("Players must remain on the board.");
			return false;
		}
		
		Location locationOnBoard = this.board.getBoard()[newLocation.getX()][newLocation.getY()];
		
		if (locationOnBoard instanceof Room.Door) {
			//we've found a Door
			Room.Door door = (Room.Door) locationOnBoard;
			if (getPrevRoom() == null) {
				//can't have been here before, haven't been anywhere yet
				return true;
			}
			if (door.getRoom().getName().equals(getPrevRoom().getName())) {
				//we were in there last turn
				System.out.println(door.getRoom().toString() + " was the last room Player " + 
				playerNumber + " was in.");
				return false;
			}
			//new Room, fine to enter
			return true;
		} else if (locationOnBoard.hasPlayer() &&
				!(locationOnBoard instanceof Room) && locationOnBoard.getPlayer().getInGame()) {
				//player is standing on this square, which isn't a room, and the player is still in the game
				System.out.println("Another player already occupies "+locationOnBoard.toString());
				//can't walk on a square already containing another player
				return false;
		} else if (locationOnBoard instanceof Room) {
			Room room = (Room) locationOnBoard;
			if (room.getName() != "Wall") {
				System.out.println("There is a wall to the " +room.getName()+" in the way.");
			} else {
				System.out.println("There is a wall in the way.");
			}
			return false;
		}
		return true;
	}
	
	/**
	 * This method should be called at the end of every turn.
	 * If the player wishes (Enters Y), they can choose to make an accusation
	 * Allow the user to choose the weapon, room, and character. If they are right,
	 * the game is over. If they are wrong, they lose the game.
	 */
	private void askForAccusation() {
		String input = inputString("Make an Accusation? (Y, N)");
		
		if (input.toUpperCase().equals("Y")) {
			//bring up an interface to allow them to choose the accusation
			ArrayList<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
			ArrayList<CharacterCard> characterCards = new ArrayList<CharacterCard>();
			ArrayList<RoomCard> roomCards = new ArrayList<RoomCard>();
			
			for (int i = 0; i < getAllCards().size(); i++) {
				Card card = getAllCards().get(i);
				if (card instanceof WeaponCard) {
					weaponCards.add((WeaponCard) card);
				} else if (card instanceof CharacterCard) {
					characterCards.add((CharacterCard) card);
				}  else if (card instanceof RoomCard) {
					roomCards.add((RoomCard) card);
				} else {
					System.out.println("Card Error");
				}
			}
			System.out.println();
			System.out.println("To make an Accusation, a Character, a Weapon and a Room must be chosen.");
			System.out.println();
			
			System.out.println("Choose a Character.");
			for (int i = 0; i < characterCards.size(); i++) {
				System.out.println(characterCards.get(i)+ " - Enter "+(i+1));
			}
			int characterInput = inputNumber("Who was the murderer? (1-"+characterCards.size()+")");
			while (characterInput < 1 || characterInput > characterCards.size()) {
				characterInput = inputNumber("Who was the murderer? (1-"+characterCards.size()+")");
			}
			CharacterCard accusedCharacter = characterCards.get(characterInput-1);
			
			System.out.println("Choose a Weapon.");
			for (int i = 0; i < weaponCards.size(); i++) {
				System.out.println(weaponCards.get(i)+ " - Enter "+(i+1));
			}
			int weaponInput = inputNumber("What was the murder Weapon? (1-"+weaponCards.size()+")");
			while (weaponInput < 1 || weaponInput > weaponCards.size()) {
				weaponInput = inputNumber("What was the murder Weapon? (1-"+weaponCards.size()+")");
			}
			WeaponCard chosenWeapon = weaponCards.get(weaponInput-1);
			
			System.out.println("Choose a Room.");
			for (int i = 0; i < roomCards.size(); i++) {
				System.out.println(roomCards.get(i)+ " - Enter "+(i+1));
			}
			int roomInput = inputNumber("Which Room did it happen in? (1-"+roomCards.size()+")");
			while (roomInput < 1 || roomInput > roomCards.size()) {
				roomInput = inputNumber("Which Room did it happen in? (1-"+roomCards.size()+")");
			}
			RoomCard chosenRoom = roomCards.get(roomInput-1);
			
			Accusation accusation = new Accusation(chosenWeapon, chosenRoom, accusedCharacter);
			System.out.println("Player "+this.playerNumber+" claims: "+accusation.toString());
			
			if (accusation.equals(getGame().getSolution())) {
				//Player has won
				System.out.println("Player "+this.playerNumber+" is correct!");
				System.out.println("Player "+this.playerNumber+ " wins.");
				System.out.println();
				getGame().setGameOver();
			} else {
				//Player has lost
				System.out.println("Player "+this.playerNumber+" was wrong.");
				System.out.println("Player "+this.playerNumber+" is out of the game.");
				setInGame(false);
				System.out.println();
				inputString("Enter to continue.");
				getBoard().displayBoard();
			}
		}
		else {
			getBoard().displayBoard();
		}
	}

	/**
	 * This method should be called at the end of every turn if the player is
	 * in a room. If the player wishes (enters Y), they can choose to make a
	 * suggestion. Allow the user to choose the weapon, and character (room will
	 * be the room they are already in). The other players will then attempt to
	 * disprove the suggestion.
	 */
	private void askForSuggestion() {
		String input = inputString("Make a Suggestion? (Y, N)");
		
		if (input.toUpperCase().equals("Y")) {
			//bring up an interface to allow them to choose the suggestion
			ArrayList<WeaponCard> weaponCards = new ArrayList<WeaponCard>();
			ArrayList<CharacterCard> characterCards = new ArrayList<CharacterCard>();
			ArrayList<RoomCard> roomCards = new ArrayList<RoomCard>();

			for (int i = 0; i < getAllCards().size(); i++) {
				Card card = getAllCards().get(i);
				if (card instanceof WeaponCard) {
					weaponCards.add((WeaponCard) card);
				} else if (card instanceof CharacterCard) {
					characterCards.add((CharacterCard) card);
				}  else if (card instanceof RoomCard) {
					roomCards.add((RoomCard) card);
				} else {
					System.out.println("Card Error");
				}
			}
			System.out.println();
			System.out.println("To make a Suggestion, a Character, a Weapon and the current Room is chosen.");
			System.out.println();
			System.out.println("Choose a Character.");
			for (int i = 0; i < characterCards.size(); i++) {
				System.out.println(characterCards.get(i)+ " - Enter "+(i+1));
			}
			int characterInput = inputNumber("Which Character do you want to suggest? (1-6)");
			while (characterInput < 1 || characterInput > 6) {
				characterInput = inputNumber("Which Character do you want to suggest? (1-6)");
			}
			CharacterCard accusedCharacter = characterCards.get(characterInput-1);
			
			System.out.println("Choose a Weapon.");
			for (int i = 0; i < weaponCards.size(); i++) {
				System.out.println(weaponCards.get(i)+ " - Enter "+(i+1));
			}
			int weaponInput = inputNumber("Which Weapon do you want to suggest? (1-6)");
			while (weaponInput < 1 || weaponInput > 6) {
				weaponInput = inputNumber("Which Weapon do you want to suggest? (1-6)");
			}
			WeaponCard chosenWeapon = weaponCards.get(weaponInput-1);
			
			RoomCard chosenRoom = null;
			//Room is preset as the room the player is in
			for (int i = 0; i < roomCards.size(); i++) {
				if (roomCards.get(i).getName().equals(((Room) getLocation()).getName())) {
					//find roomCard with the same name as the room player is int
					chosenRoom = roomCards.get(i);
				}
			}
			
			Suggestion suggestion = new Suggestion(chosenWeapon, chosenRoom, accusedCharacter);
			System.out.println("Player "+this.playerNumber+" is suggesting: "+suggestion.toString());
		}
	}
	
	/**
	 * This method should be called to prove/disprove a
	 * suggestion. From left of the player, the cards of
	 * each player are checked to see if their hand
	 * contains any of the cards in the suggestion.
	 * If any hand contains one of the cards, that card
	 * is displayed, and false is returned. If the suggestion
	 * cannot be disproved, return true.
	 * @param suggestion
	 * @return
	 */
	public boolean checkSuggestion(Suggestion suggestion) {
		int numPlayers = getGame().getNumPlayers();
		ArrayList<Player> players = getGame().getListOfPlayers();
		for (int i = getPlayerNumber(); i < getPlayerNumber()+numPlayers; i++) {
			Player checkPlayer = players.get(i%numPlayers); //check other players
			for (Card card: checkPlayer.getCards()) {
				if (suggestion.compare(card) != null) {
					//another player can disprove the suggestion
					System.out.println("Player "+getPlayerNumber()+"'s suggestion was incorrect.");
					System.out.println("Player "+i+"'s hand contains "+card.toString()+".");
					return false;
				}
			}
		}
		System.out.println("Player "+getPlayerNumber()+", no one could disprove your suggestion.");
		return true;
	}

	public ArrayList<Card> getAllCards() {
		return allCards;
	}

	public void setAllCards(ArrayList<Card> allCards) {
		this.allCards = allCards;
	}
	
	public ArrayList<Card> getCards() {
		return this.hand;
	}
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public void setBoard(Board b) {
		this.board = b;
	}

	public CharacterCard getCharacter() {
		return this.character;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public boolean getInGame() {
		return this.inGame;
	}
	
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (hand == null) {
			if (other.hand != null)
				return false;
		} else if (!hand.equals(other.hand))
			return false;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character))
			return false;
		if (inGame != other.inGame)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		result = prime * result + ((character == null) ? 0 : character.hashCode());
		result = prime * result + (inGame ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}
	
	public Room getPrevRoom() {
		return prevRoom;
	}

	public void setPrevRoom(Room prevRoom) {
		this.prevRoom = prevRoom;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		setBoard(game.getBoard());
		setAllCards(game.getAllCards());
	}

	/**
	 * This method should be called to get a text input from the user.
	 * The message string is printed, and then user input is taken. The user
	 * is repeatedly prompted to enter a text until they do so.
	 * @param message
	 * @return input text
	 */
	private static String inputString(String string){
		System.out.print(string + " ");
		System.out.println();
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				return input.readLine().toUpperCase();
			}
			catch (IOException e) {}
		}
	}
	
	/**
	 * This method should be called to get a number input from the user.
	 * The message string is printed, and then user input is taken. The user
	 * is repeatedly prompted to enter a number until they do so.
	 * @param message
	 * @return input number
	 */
	private static int inputNumber(String string) {
		System.out.print(string + " ");
		System.out.println();
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (IOException e) {
				System.out.println("Please enter a number!");
			} catch (NumberFormatException e) {
				System.out.println("Please enter a number!");
			}
		}
	
	}
	
}
