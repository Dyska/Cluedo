import java.util.*;

public class Game {
	private Board board;
	private ArrayList<Card> allCards;
	private ArrayList<Card> listOfCards;
	private ArrayList<Player> listOfPlayers;
	private int numPlayers;
	private Solution solution;
	private boolean gameOver;
	
	public Game(int numPlayers) {
		this.numPlayers = numPlayers;
		this.listOfCards = initialiseCards(); //make all the cards
		this.allCards = (ArrayList<Card>) this.listOfCards.clone(); //make a copy of all cards
		this.listOfPlayers = initialisePlayers(); //make all the players, assign each a character
		this.board = new Board(listOfPlayers); //make a new board
		placeWeapons(); //place all the weapons in random rooms
		this.solution = initialiseSolution(); //make a solution
		dealCards(); //deal out the remaining cards
		this.board.displayBoard();
	}
	
	/**
	 * This method should create all the cards that will be used.
	 * This includes 6 CharacterCards, 6 WeaponCards, and 9
	 * RoomCards. 
	 * @return a list of the cards made.
	 */
	private ArrayList<Card> initialiseCards() {
		ArrayList<Card> cards = new ArrayList<Card>();
		
		//Add all CharacterCards
		cards.add(new CharacterCard("Miss Scarlett"));
		cards.add(new CharacterCard("Colonel Mustard"));
		cards.add(new CharacterCard("Mrs. White"));
		cards.add(new CharacterCard("The Reverend Green"));
		cards.add(new CharacterCard("Mrs. Peacock"));
		cards.add(new CharacterCard("Professor Plum"));
		
		//Add all WeaponCards
		cards.add(new WeaponCard("Candlestick"));
		cards.add(new WeaponCard("Dagger"));
		cards.add(new WeaponCard("Lead Pipe"));
		cards.add(new WeaponCard("Revolver"));
		cards.add(new WeaponCard("Rope"));
		cards.add(new WeaponCard("Spanner"));

		//Add all RoomCards
		cards.add(new RoomCard("Kitchen"));
		cards.add(new RoomCard("Ball Room"));
		cards.add(new RoomCard("Conservatory"));
		cards.add(new RoomCard("Dining Room"));
		cards.add(new RoomCard("Billiard Room"));
		cards.add(new RoomCard("Library"));
		cards.add(new RoomCard("Lounge"));
		cards.add(new RoomCard("Hall"));
		cards.add(new RoomCard("Study"));

		return cards;
	}
	
	/**
	 * This method should initialise the correct number of players,
	 * and assign each a CharacterCard. It should print out which
	 * character each player has been assigned.
	 * @return
	 */
	private ArrayList<Player> initialisePlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		ArrayList<CharacterCard> cards = new ArrayList<CharacterCard>();
		
		cards.add(new CharacterCard("Miss Scarlett"));
		cards.add(new CharacterCard("Colonel Mustard"));
		cards.add(new CharacterCard("Mrs. White"));
		cards.add(new CharacterCard("The Reverend Green"));
		cards.add(new CharacterCard("Mrs. Peacock"));
		cards.add(new CharacterCard("Professor Plum"));

		//we want to generate a random number between 0 and the number of cards
		for (int i=1; i <= this.numPlayers; ++i) {
			int random = (int) Math.round((Math.random()*cards.size()));
			if (random == cards.size()) { random--; }
			CharacterCard card = cards.get(random);
			//need to make sure this card hasn't been drawn yet
			players.add(new Player(i, card));
			cards.remove(card);
			System.out.println("Player " + i + ", you will be playing as " + card.toString() +".");
		}
		System.out.println();
		return players;
	}
			
	/**
	 * This method should randomly select a weapon, a character,
	 * and a room. It should store these in a Solution object and
	 * assign this to the solution field.
	 * @return
	 */
	private Solution initialiseSolution() {
		CharacterCard character = null;
		RoomCard room = null;
		WeaponCard weapon = null;
		
		while (character == null || room == null || weapon == null) {
			int random = (int) Math.round(Math.random()*this.listOfCards.size());
			if (random == this.listOfCards.size()) { random--; } //make sure we don't get an ArrayIndexOutOfBoundsException
			Card card = this.listOfCards.get(random);
			
			if (card instanceof CharacterCard && character == null) {
				//we've drawn a CharacterCard, and we haven't chosen a character in the Solution yet
				character = (CharacterCard) card;
				this.listOfCards.remove(random);
			}
			else if (card instanceof RoomCard && room == null) {
				//we've drawn a RoomCard, and we haven't chosen a room in the Solution yet
				room = (RoomCard) card;
				this.listOfCards.remove(random);
			} 
			else if (card instanceof WeaponCard && weapon == null) {
				//we've drawn a WeaponCard, and we haven't chosen a weapon in the Solution yet
				weapon = (WeaponCard) card;
				this.listOfCards.remove(random);
			}
		}
		System.out.println("The solution to the murder has been stored.");
		System.out.println();
		return new Solution(room, character, weapon);
	}
	
	/**
	 * This method should assign every weapon to a random room.
	 * Each room can contain at most one weapon.
	 */
	private void placeWeapons() {
		ArrayList<Room> rooms = this.board.getRooms();
		for (int j=0; j < listOfCards.size(); j++) {
			//place all cards in the pack
			if (this.listOfCards.get(j) instanceof WeaponCard) {
				WeaponCard weapon = (WeaponCard) this.listOfCards.get(j);
				int random = (int) Math.round(Math.random() * rooms.size());
				if (random == rooms.size()) {random--;}
				weapon.setRoom(rooms.get(random));
				System.out.println("The " + weapon.toString() + " has been placed in the " + weapon.getRoom().toString() + ".");
				rooms.remove(random);
			}
		}
		System.out.println();
	}
	
	/**
	 * This method should deal the remaining 18 cards evenly among
	 * the players.
	 */
	private void dealCards() {
		while (!this.listOfCards.isEmpty()) {
			Player player = this.listOfPlayers.get(this.listOfCards.size() % this.numPlayers); //this players turn to be dealt to
			Card newCard = this.listOfCards.get(0); //just take the top card of the deck
			player.dealCard(newCard); //deal the top card to the player
			newCard.setPlayer(player); //set the cards owner as the player
			this.listOfCards.remove(0); //remove the card from the deck
		}		
		for (int i=1; i <= this.numPlayers; ++i) {
			List<Card> cards = this.listOfPlayers.get(i-1).getCards();
			System.out.println("Player " + i +", your cards are: " + cards.toString());
		}
		System.out.println();
	}
	
	/**
	 * This method should begin playing the game. This method should
	 * 
	 */
	public void playGame() {
		Player currentPlayer;
		int currentPlayers = this.listOfPlayers.size();
		while (!gameOver) {
			for (int i = 0; i < this.listOfPlayers.size(); i++) {
				if (currentPlayers > 1 && !gameOver) {
					currentPlayer = this.listOfPlayers.get(i);
					if (currentPlayer.getInGame()) {
						//make move
						currentPlayer.setGame(this);
						currentPlayer.takeTurn(false);	
					}
					//check if a player just lost
					if (!currentPlayer.getInGame()) {
						//one less player
						currentPlayers--;
						if (currentPlayers == 1) {
							//last player remaining
							gameOver();
							return;
						}
					}
					//check if game is finished
					if (gameOver) {
						gameOver();
						return;
					}
				}
				//there is only 1 player left, they must
				//have won
				else {
					gameOver();
					return;
				}
			}
		}
		//game must be over
		gameOver();
		return;	
	}
	
	/**
	 * This method should be called when the game is over. The list
	 * of players is iterated through, and the (only) player that is
	 * still in the game is declared the winner.
	 */
	private void gameOver() {
		setGameOver();
		for (int i = 0; i < this.listOfPlayers.size(); i++) {
			//should only be 1 player left
			if (this.listOfPlayers.get(i).getInGame()) {
				System.out.println("Player "+
			this.listOfPlayers.get(i).getPlayerNumber() + " is the winner!");
				break;
			}
		}
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}

	public ArrayList<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public void setGameOver() {
		this.gameOver = true;
	}

	public Solution getSolution() {
		return solution;
	}

	public ArrayList<Card> getAllCards() {
		return allCards;
	}

	public Board getBoard() {
		return board;
	}
}
