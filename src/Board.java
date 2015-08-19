import java.util.*;

public class Board {	
	private Location[][] board;
	private ArrayList<Player> players; 
	private ArrayList<Room> stationaryObjects;
	
	/**
	 * Create a board 25 by 25 squares to play on. The
	 * board is a 2D array of Locations.
	 * @param players
	 */
	public Board(ArrayList<Player> players) {
		this.board = new Location[25][25]; //board is 25 by 25 squares
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = new Location(i,j);
			}
		}
		//initialise all parts of the board
		this.players = players;
		this.stationaryObjects = initialiseRooms();
		makeRooms();
		initialiseSecretPassageWays();
		initialisePlayerLocations();
	}
	
	/**
	 * This method should return a list of all rooms in
	 * stationaryObjects that are actually rooms, ie
	 * not doors or the wall.
	 * @return
	 */
	public ArrayList<Room> getRooms() {
		//return all rooms (Not doors or wall)
		ArrayList<Room> roomsOnly = new ArrayList<Room>();
		for (int i=0; i < this.stationaryObjects.size(); i++) {
			String name = this.stationaryObjects.get(i).getName();
			if (!(name.endsWith("Door")) && name != "Wall") {
				roomsOnly.add(this.stationaryObjects.get(i));
			}
		}
		return roomsOnly;
	}
	
	/**
	 * This method should call the createRoomSpace for
	 * each stationaryObject. This will 'make' the room
	 * on the board.
	 */
	
	private void makeRooms() {
		for (int i=0; i < this.stationaryObjects.size(); i++) {
			createRoomSpace(this.stationaryObjects.get(i).getName());
		}
	}

	/**
	 * This method should place each player in a different
	 * starting location, based on the character they were
	 * assigned.
	 */
	private void initialisePlayerLocations() {
		for (int i = 0; i < this.players.size(); i++) {
			Player player = this.players.get(i);
			String characterName = player.getCharacter().toString();
			Location playerLocation = null;
			
			//place each character in their starting positions
			
			switch (characterName) {
				case "Miss Scarlett":
					playerLocation = this.board[24][8];
					break;
				case "Colonel Mustard":
					playerLocation = this.board[0][15];
					break;
				case "Mrs. White":
					playerLocation = this.board[17][0];
					break;
				case "The Reverend Green":
					playerLocation = this.board[0][10];
					break;
				case "Mrs. Peacock":
					playerLocation = this.board[6][24];
					break;
				case "Professor Plum":
					playerLocation = this.board[19][24];
					break;
				default:
					System.out.println("Error.");
					break;
			}
			playerLocation.addPlayer(player);
			player.setLocation(playerLocation);
		}
	}
	
	/**
	 * This method should initialise the Room and Room.Door
	 * objects. It should add them to the list of stationary
	 * objects, which is then returned.
	 * @return
	 */
	
	private ArrayList<Room> initialiseRooms() {
		ArrayList<Room> stationaryObjects= new ArrayList<Room>();
		//for each room, add the room to the list, and add its door(s)
		Room kitchen = new Room("Kitchen");
		stationaryObjects.add(kitchen);
		stationaryObjects.add(new Room.Door("Kitchen Door", kitchen));
		
		Room ballRoom = new Room("Ball Room");
		stationaryObjects.add(ballRoom);
		stationaryObjects.add(new Room.Door("Ball Room Door", ballRoom));
		
		Room conservatory = new Room("Conservatory");
		stationaryObjects.add(conservatory);
		stationaryObjects.add(new Room.Door("Conservatory Door", conservatory));
		
		Room diningRoom = new Room("Dining Room");
		stationaryObjects.add(diningRoom);
		stationaryObjects.add(new Room.Door("Dining Room Door", diningRoom));
		
		Room billiardRoom = new Room("Billiard Room"); 
		stationaryObjects.add(billiardRoom);
		stationaryObjects.add(new Room.Door("Billiard Room Door", billiardRoom)); 
		
		Room library = new Room("Library"); 
		stationaryObjects.add(library);
		stationaryObjects.add(new Room.Door("Library Door", library)); 
		
		Room lounge = new Room("Lounge");
		stationaryObjects.add(lounge);
		stationaryObjects.add(new Room.Door("Lounge Door", lounge));
		
		Room hall = new Room("Hall");
		stationaryObjects.add(hall);
		stationaryObjects.add(new Room.Door("Hall Door", hall)); 
		
		Room study = new Room("Study");
		stationaryObjects.add(study); 
		stationaryObjects.add(new Room.Door("Study Door", study));
		
		stationaryObjects.add(new Room("Wall")); //Walls don't have any doors
		
		return stationaryObjects;
	}
	
	/**
	 * This method should draw on the board exactly
	 * where a certain room should go. It should also draw in
	 * all doors for that room.
	 * @param name of Room to draw.
	 */
	private void createRoomSpace(String name) {
		switch (name) {
			case "Kitchen":
				for (int i = 1; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						board[i][j] = stationaryObjects.get(0);
					}
				}
				//1 door
				board[6][5] = stationaryObjects.get(1);
				break;
	
			case "Dining Room": 
				for (int i = 9; i < 10; i++) {
					for (int j = 0; j < 6; j++) {
						board[i][j] = stationaryObjects.get(6);
					}
				}
				for (int i = 10; i < 16; i++) {
					for (int j = 0; j < 9; j++) {
						board[i][j] = stationaryObjects.get(6);
					}
				}
				//2 doors
				board[12][8] = stationaryObjects.get(7); 
				board[15][7] = stationaryObjects.get(7);
				
				
				break;
			case "Lounge":
				for (int i = 19; i < 25; i++) {
					for (int j = 0; j < 8; j++) {
						board[i][j] = stationaryObjects.get(12);
					}
				}
				//1 door
				board[19][7] = stationaryObjects.get(13);
				break;
			case "Hall":
				for (int i = 18; i < 25; i++) {
					for (int j = 10; j < 16; j++) {
						board[i][j] = stationaryObjects.get(14);;
					}
				}
				//3 doors
				board[18][12] = stationaryObjects.get(15);
				board[18][13] = stationaryObjects.get(15);
				board[20][15] = stationaryObjects.get(15);
				break;
			case "Study":
				for (int i = 21; i < 25; i++) {
					for (int j = 18; j < 25; j++) {
						board[i][j] = stationaryObjects.get(16);;
					}
				}
				//1 door
				board[21][18] = stationaryObjects.get(17);
				break;
			case "Library":
				for (int i = 14; i < 19; i++){
					for (int j = 19; j < 25; j++){
						board[i][j] = stationaryObjects.get(10);;
					}
				}
				board[15][18] = stationaryObjects.get(10);
				board[17][18] = stationaryObjects.get(10);
				//2 doors
				board[16][18] = stationaryObjects.get(11);
				board[14][21] = stationaryObjects.get(11);
				break;
			case "Billiard Room":
				for (int i = 8; i < 13; i++){
					for (int j = 19; j < 25; j++){
						board[i][j] = stationaryObjects.get(8);;
					}
				}
				//2 doors
				board[12][23] = stationaryObjects.get(9);
				board[9][19] = stationaryObjects.get(9);
				break;
			case "Conservatory": 
				for (int i = 1; i < 6; i++){
					for (int j = 19; j < 25; j++){
						if (i == 5 && j == 19) { 
							//don't draw the corner square
						} else {
							board[i][j] = stationaryObjects.get(4);
						}							
					}
				}
				//1 door
				board[4][19] = stationaryObjects.get(5);
				break;
			case "Ball Room":
				for (int i = 1; i < 2; i++){
					for (int j = 11; j < 15; j++){
						board[i][j] = stationaryObjects.get(2);;
					}
				} 
				
				for (int i = 2; i < 8; i++){
					for (int j = 9; j < 17; j++){
						board[i][j] = stationaryObjects.get(2);;
					}
				}
				//4 doors
				board[5][16] = stationaryObjects.get(3);
				board[7][15] = stationaryObjects.get(3);
				board[7][10] = stationaryObjects.get(3);
				board[5][9] = stationaryObjects.get(3);
				break;
			case "Wall":
				//draw in all the walls
				for (int i=0; i < 25; i++) {
					if (i != 10 && i != 15) {
						board[0][i] = stationaryObjects.get(18);
					}
				}
				for (int i=10; i < 17;i++) {
					for (int j=11; j < 16; j++) {
						board[i][j] = stationaryObjects.get(18);

					}
				}
				//Extra wall squares
				board[1][7] = stationaryObjects.get(18);
				board[8][0] = stationaryObjects.get(18);
				board[1][18] = stationaryObjects.get(18);
				board[7][24] = stationaryObjects.get(18);
				board[13][24] = stationaryObjects.get(18);
				board[20][24] = stationaryObjects.get(18);
				board[16][0] = stationaryObjects.get(18);
				board[18][0] = stationaryObjects.get(18);
				board[24][9] = stationaryObjects.get(18);
				board[24][16] = stationaryObjects.get(18);
				break;
			default:
				break;
		}
	}
	
	
	/**
	 * This method should initialise the secret passageways
	 * that link different rooms on the board. These links are:
	 * Kitchen to Study, and vise versa,
	 * and Conservatory to Lounge, and vise versa.
	 * Unlike other rooms, if we take a secret passageway, then
	 * we have not entered this room from a door before. 
	 * Therefore we need to set default doors to exit from.
	 */
	private void initialiseSecretPassageWays(){	
		Room kitchen = getRooms().get(0);
		Room study = getRooms().get(8);
		
		kitchen.setSecretPassage(study); //kitchen links to study
		study.setSecretPassage(kitchen); //study links to kitchen
		
		//set default doors
		Room.Door kitchenDoor = (Room.Door) this.board[6][5];
		kitchenDoor.setX(6); kitchenDoor.setY(5);
		kitchen.setDoor(kitchenDoor);
		Room.Door studyDoor = (Room.Door) this.board[21][18];
		studyDoor.setX(21); studyDoor.setY(18);
		study.setDoor(studyDoor);
		
		Room lounge = getRooms().get(6);
		Room conservatory = getRooms().get(2);
		
		lounge.setSecretPassage(conservatory); //lounge links to conservatory
		conservatory.setSecretPassage(lounge); //conservatory links to lounge
		
		//set default doors
		Room.Door loungeDoor = (Room.Door) this.board[19][7];
		loungeDoor.setX(19); loungeDoor.setY(7);
		lounge.setDoor(loungeDoor);
		Room.Door conservatoryDoor = (Room.Door) this.board[4][19];
		conservatoryDoor.setX(4); conservatoryDoor.setY(19);
		conservatory.setDoor(conservatoryDoor);
	}
	
	/**
	 * This method should be called to print out the board.
	 * To do this, each individual square of the board
	 * has the .draw() method called on it.
	 */


	public void displayBoard() {
		for (int i = 0; i < board.length; i++) {
			System.out.println();
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j].draw());
			}
		}
		System.out.println();
		System.out.println();
	}
	
	/**
	 * Return the board.
	 * @return
	 */

	public Location[][] getBoard() {
		return board;
	}

}
