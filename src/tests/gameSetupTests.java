package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;
	private static Card human;
	private static Card computer1;
	private static Card computer2;
	private static Card room1;
	private static Card room2;
	private static Card room3;
	private static Card weapon1;
	private static Card weapon2;
	
	@BeforeClass
	public static void setUp(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our config files
		board.setConfigFiles("data/boardLayout.csv", "data/layout.txt", "data/people.txt", "data/weapons.txt");		
		// Initialize will load all config files 
		board.initialize();
		
		human = new Card("Human", CardType.PERSON);
		computer1 = new Card("Rader", CardType.PERSON);
		computer2 = new Card("CPW", CardType.PERSON);
		room1 = new Card("Alderson", CardType.ROOM);
		room2 = new Card("Berthoud", CardType.ROOM);
		room3 = new Card("Hill", CardType.ROOM);
		weapon1 = new Card("Acid", CardType.WEAPON);
		weapon2 = new Card("Hammer", CardType.WEAPON);
	}
	
	@Test
	//This tests that all of the people are loaded correctly by testing that the human player and first and last computer players have the correct colors and starting locations
	public static void testloadPeople(){
		Map<String,Player> people = board.getPlayers();
		//test human
		Player testhuman = people.get("Human");
		assertEquals(Color.blue, testhuman.getColor());
		assertEquals(0, testhuman.getRow());
		assertEquals(6, testhuman.getCol());
		//test first computer player
		Player testComputer1 = people.get("Rader");
		assertEquals(Color.red, testComputer1.getColor());
		assertEquals(0, testComputer1.getRow());
		assertEquals(18, testComputer1.getCol());
		//test last computer player
		Player testComputer2 = people.get("CPW");
		assertEquals(Color.magenta, testComputer2.getColor());
		assertEquals(10, testComputer2.getRow());
		assertEquals(0, testComputer2.getCol());
	
	}
	
}
