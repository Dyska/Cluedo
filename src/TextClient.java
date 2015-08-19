import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextClient {	

	/**
	 * Main method of Cluedo. This method is called to initialise a game.
	 * Number of players is prompted, and then a new Game is created.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Cluedo!");
		int numPlayers = inputNumber("How many players are in the game?");
		while (numPlayers < 1 || numPlayers > 6) {
			numPlayers = inputNumber("Please enter a number between 1 and 6:");
		}
		System.out.println();
		Game cluedo = new Game(numPlayers);
		cluedo.playGame();
	}
	
	/**
	 * This method should be called to get a number input from the user.
	 * The message string is printed, and then user input is taken. The user
	 * is repeatedly prompted to enter a number until they do so.
	 * @param message
	 * @return input number
	 */
	public static int inputNumber(String message) {
		System.out.print(message + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (IOException e) {
				System.out.println("Please enter a number!");
			}  catch (NumberFormatException e) {
				System.out.println("Please enter a number!");
			}
		}
	}
}
