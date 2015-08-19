import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class CluedoTests {

	@Test
	public void testValidAccusation1(){
		RoomCard lounge = new RoomCard("Lounge");
		WeaponCard gun = new WeaponCard("Gun");
		CharacterCard mustard = new CharacterCard("Col Mustard");
		Accusation a = new Accusation(gun,lounge,mustard);
		Solution sol = new Solution(lounge,mustard,gun);
		assertTrue (a.equals(sol));
	}

	@Test
	public void testValidAccusation2(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Accusation a = new Accusation(pipe,conservatory,plum);
		Solution sol = new Solution(conservatory,plum,pipe);
		assertTrue (a.equals(sol));
	}
	
	@Test
	public void testInput1(){
		TextClient t = new TextClient();
		int j = 3;
		int i= 0;
		while (i !=3){
		i = t.inputNumber("Enter the number 3");
		}
		assertTrue(i==j);
	}
	
	@Test
	public void testInput2(){
		TextClient t = new TextClient();
		int j = 30;
		int i= 0;
		while (i !=30){
		i = t.inputNumber("Enter the number 30");
		}
		assertTrue(i==j);
	}
	
	@Test
	public void testLocation1(){
		Location l = new Location(0,0);
		CharacterCard plum = new CharacterCard("Professor Plum");
		Player player = new Player(1, plum);
		l.addPlayer(player);
		assertTrue(l.getPlayer()==player);
	}
	
	@Test
	public void testLocation2(){
		Location l = new Location(0,0);
		CharacterCard plum = new CharacterCard("Professor Plum");
		Player player = new Player(1, plum);
		l.addPlayer(player);
		l.removePlayer();
		assertTrue(l.getPlayer()==null);
	}
	
	@Test
	public void testLocation3(){
		Location l = new Location(10,10);
		assertTrue(l.getX()==10 && l.getY()==10);
	}
	
	@Test
	public void testLocation4(){
		Location l = new Location(10,10);
		assertFalse(l.hasPlayer());
	}
	
	@Test
	public void testSuggestionReturn1(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Suggestion s =new Suggestion(pipe,conservatory,plum);
		assertTrue(s.getCharacter()==plum);
	}
	

	@Test
	public void testSuggestionReturn2(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Suggestion s =new Suggestion(pipe,conservatory,plum);
		assertTrue(s.getRoom()==conservatory);
	}
	
	@Test
	public void testSuggestionReturn3(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Suggestion s =new Suggestion(pipe,conservatory,plum);
		assertTrue(s.getWeapon()==pipe);
	}
	
	@Test
	public void testAccusationReturn1(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Accusation a =new Accusation(pipe,conservatory,plum);
		assertTrue(a.getCharacter()==plum);
	}
	

	@Test
	public void testAccusationReturn2(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Accusation a =new Accusation(pipe,conservatory,plum);
		assertTrue(a.getRoom()==conservatory);
	}
	
	@Test
	public void testAccusationReturn3(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Accusation a =new Accusation(pipe,conservatory,plum);
		assertTrue(a.getWeapon()==pipe);
	}
	
	@Test
	public void testInvalidAccusation1(){
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard pipe = new WeaponCard("Pipe");
		WeaponCard gun = new WeaponCard("Gun");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Accusation a = new Accusation(pipe,conservatory,plum);
		Solution sol = new Solution(conservatory,plum,gun);
		assertFalse (a.equals(sol));
	}
	
	@Test
	public void testInvalidAccusation2(){
		RoomCard conservatory = new RoomCard("Conservatory");
		RoomCard lounge = new RoomCard("Lounge");
		WeaponCard gun = new WeaponCard("Gun");
		CharacterCard mustard = new CharacterCard("Col Mustard");
		WeaponCard pipe = new WeaponCard("Pipe");
		CharacterCard plum = new CharacterCard("Professor Plum");
		Accusation a = new Accusation(pipe,conservatory,plum);
		Solution sol = new Solution(lounge,mustard,gun);
		assertFalse (a.equals(sol));
	}
	
	@Test
	public void testInValidCompare1(){
		RoomCard lounge = new RoomCard("Lounge");
		RoomCard conservatory = new RoomCard("Conservatory");
		WeaponCard gun = new WeaponCard("Gun");
		CharacterCard mustard = new CharacterCard("Col Mustard");
		Suggestion s = new Suggestion(gun,lounge,mustard);
		assert(s.compare(conservatory)==null);
	}

	@Test
	public void testValidCompare1(){
		RoomCard lounge = new RoomCard("Lounge");
		WeaponCard gun = new WeaponCard("Gun");
		CharacterCard mustard = new CharacterCard("Col Mustard");
		Suggestion s = new Suggestion(gun,lounge,mustard);
		assert(s.compare(mustard) == mustard);
	}
	
	@Test
	public void testValidCompare2(){
		RoomCard lounge = new RoomCard("Lounge");
		WeaponCard gun = new WeaponCard("Gun");
		CharacterCard mustard = new CharacterCard("Col Mustard");
		Suggestion s = new Suggestion(gun,lounge,mustard);
		assert(s.compare(lounge) == lounge);
	}
	
	@Test
	public void testValidCompare3(){
		RoomCard lounge = new RoomCard("Lounge");
		WeaponCard gun = new WeaponCard("Gun");
		CharacterCard mustard = new CharacterCard("Col Mustard");
		Suggestion s = new Suggestion(gun,lounge,mustard);
		assert(s.compare(gun) == gun);
	}
	
	@Test
	public void testValidDice() {
		Player p = new Player(1, new CharacterCard("Miss Scarlett"));
		for (int i=0; i < 100; i++) {
			int roll = p.rollDice();
			assert(roll >= 2);
			assert(roll <= 12);
		}
	}
}
