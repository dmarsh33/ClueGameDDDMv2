package tests;

import java.awt.Color;
import java.util.*;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	private static Card human; 
	private static Card computer1;
	private static Card computer2;
	private static Card computer3;
	private static Card computer4;
	private static Card room1;
	private static Card weapon1;
	private static Card weapon2;
	private static Card weapon3;
	private static Card weapon4;
	private static Card weapon5;
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
		computer3 = new Card("Sava", CardType.PERSON);
		computer4 = new Card("Snieder", CardType.PERSON);
		room1 = new Card("Alderson", CardType.ROOM);
		weapon1 = new Card("Acid", CardType.WEAPON);
		weapon2 = new Card("Hammer", CardType.WEAPON);
		weapon3 = new Card("Laser", CardType.WEAPON);
		weapon4 = new Card("Quartz", CardType.WEAPON);
		weapon5 = new Card("Beaker", CardType.WEAPON);
	}
	
	@Test
	//This tests selecting a target location for the computer player
	public void selectTarget(){
		//Testing that if player cant enter room, target is selected randomly, pathlength = 3
		board.calcTargets(0,18,3);
		Set<BoardCell> targets=board.getTargets(); 
		ComputerPlayer testNoRooms = new ComputerPlayer("Rader", 0,18,Color.red);
		int loc1 = 0;
		int loc2 = 0;
		int loc3 = 0;
		int loc4 = 0;
		int other = 0;
		for(int i = 1; i < 100; i++){
			BoardCell location = testNoRooms.pickLocation(targets, board.getCellAt(0, 18));	
			if(location.getRow()==0 && location.getColumn()==17){
				loc1++;
			}
			else if(location.getRow()==1 && location.getColumn()==18){
				loc2++;
			}
			else if(location.getRow()==2 && location.getColumn()==17){
				loc3++;
			}
			else if(location.getRow()==3 && location.getColumn()==18){
				loc4++;
			}
			else {
				other++;
			}
		}
		assertTrue(loc1>0);
		assertTrue(loc2>0);
		assertTrue(loc3>0);
		assertTrue(loc4>0);
		assertTrue(other==0);
		//Testing that if player cant enter room, target is selected randomly, pathlength = 2
		board.calcTargets(10,16,2);
		targets=board.getTargets(); 
		ComputerPlayer testNoRooms2 = new ComputerPlayer("Rader", 10,16,Color.red);
		loc1 = 0;
		loc2 = 0;
		loc3 = 0;
		loc4 = 0;
		int loc5 = 0;
		other = 0;
		for(int i = 1; i < 100; i++){
			BoardCell location = testNoRooms2.pickLocation(targets, board.getCellAt(10,16));	
			if(location.getRow()==8 && location.getColumn()==16){
				loc1++;
			}
			else if(location.getRow()==9 && location.getColumn()==17){
				loc2++;
			}
			else if(location.getRow()==10 && location.getColumn()==18){
				loc3++;
			}
			else if(location.getRow()==11 && location.getColumn()==17){
				loc4++;
			}
			else if(location.getRow()==12 && location.getColumn()==16){
				loc5++;
			}
			else {
				other++;
			}
		}
		assertTrue(loc1>0);
		assertTrue(loc2>0);
		assertTrue(loc3>0);
		assertTrue(loc4>0);
		assertTrue(loc5>0);
		assertTrue(other==0);
		
		//Testing that if there is a room in the list that was not just visited, the player must select it
		board.calcTargets(17,17,2);
		targets = board.getTargets();
		ComputerPlayer testRoom = new ComputerPlayer("Rader", 17,17,Color.red);
		BoardCell location = testRoom.pickLocation(targets, board.getCellAt(17,17));
		assertTrue(location.getRow()==17 && location.getColumn()==19);
		//same thing different location. Starting location is another room
		board.calcTargets(7,5,6);
		targets = board.getTargets();
		ComputerPlayer testRoom2 = new ComputerPlayer("Rader", 7,5,Color.red);
		location = testRoom2.pickLocation(targets, board.getCellAt(7,5));
		assertTrue(location.getRow()==3 && location.getColumn()==6);
		
		//Testing that if a room was just visited, and player can visit the room again, location is randomly selected not including the room
		board.calcTargets(15,22,3);
		targets= board.getTargets();
		ComputerPlayer testInRoom = new ComputerPlayer("Rader", 15,22, Color.red);
		loc1 = 0;
		loc2 = 0;
		loc3 = 0;
		loc4 = 0;
		loc5 = 0;
		other = 0;
		for(int i = 1; i < 100; i++){
			location = testInRoom.pickLocation(targets, board.getCellAt(15,22));	
			if(location.getRow()==14 && location.getColumn()==20){
				loc1++;
			}
			else if(location.getRow()==13 && location.getColumn()==21){
				loc2++;
			}
			else if(location.getRow()==15 && location.getColumn()==21){
				loc3++;
			}
			else if(location.getRow()==13 && location.getColumn()==23){
				loc4++;
			}
			else if(location.getRow()==14 && location.getColumn()==24){
				loc5++;
			}
			else {
				other++;
			}
		}
		assertTrue(loc1>0);
		assertTrue(loc2>0);
		assertTrue(loc3>0);
		assertTrue(loc4>0);
		assertTrue(loc5>0);
		assertTrue(other==0);		
	}
	
	@Test 
	//Tests checking an accusation
	public void checkAccusationTest(){
		//deals the cards 
		board.dealCards();
		//Tests that the entire solution is correct
		Solution testCorrect = board.getSolution();
		assertTrue(board.checkAccusation(testCorrect));
		//Tests soluton with incorrect weapon
		Solution testIncorrectWeapon = new Solution(board.getSolution().getPerson(), "x", board.getSolution().getRoom());
		assertFalse(board.checkAccusation(testIncorrectWeapon));
		//Tests solution with incorrect person
		Solution testIncorrectPerson = new Solution("x", board.getSolution().getWeapon(), board.getSolution().getRoom());
		assertFalse(board.checkAccusation(testIncorrectPerson));
		//Tests solution with incorrect room
		Solution testIncorrectRoom = new Solution(board.getSolution().getPerson(), board.getSolution().getWeapon(), "x");
		assertFalse(board.checkAccusation(testIncorrectRoom));
	}
	
	@Test
	//Tests that the suggestion is created
	public void createSuggestionTest(){
		board.dealCards();
		ComputerPlayer test = new ComputerPlayer("Rader", 15, 21, Color.red);
		//creating a test hand of cards seen
		Set<Card> testHand = new HashSet<Card>();
		testHand.add(human);
		testHand.add(room1);
		testHand.add(weapon1);
		//tests to see if the suggestion room is the same room the player is in for two rooms
		/*Solution correctRoom = test.createSuggestion(testHand, board);
		assertTrue(correctRoom.getRoom().equals("Brown"));
		ComputerPlayer test2 = new ComputerPlayer("CPW", 3, 6, Color.red);
		Solution correctRoom2 = test2.createSuggestion(testHand, board);
		assertTrue(correctRoom2.getRoom().equals("Hill"));*/
		//Tests if only one weapon or person is not seen, it is selected as a suggestion
		testHand.add(computer1);
		testHand.add(computer2);
		testHand.add(computer3);
		testHand.add(computer4);
		testHand.add(weapon2);
		testHand.add(weapon3);
		testHand.add(weapon4);
		testHand.add(weapon5);
		Solution onlyOneLeft = test.createSuggestion(testHand, board);
		System.out.println(onlyOneLeft.getPerson() + " " + onlyOneLeft.getWeapon());
		assertTrue(onlyOneLeft.getPerson().equals("Hellman") && onlyOneLeft.getWeapon().equals("Gravimeter"));
		//Tests if the suggestion is randomly chosen when more than one card is not seen of each type
		/*testHand.clear();
		testHand.add(human);
		testHand.add(room1);
		testHand.add(weapon1);
		testHand.add(computer1);
		testHand.add(computer2);
		testHand.add(weapon2);
		testHand.add(weapon3);
		int comp3 = 0;
		int comp4 = 0;
		int comp5 = 0;
		int w3 = 0;
		int w4 = 0;
		int w5 = 0;
		int other = 0;
		for(int i = 1; i < 100; i++){
			Solution randomTest = test.createSuggestion(testHand, board);
			if(randomTest.getPerson().equals("Snieder")){
				comp3++;
			}
			else if(randomTest.getPerson().equals("Sava")){
				comp4++;
			}
			else if(randomTest.getPerson().equals("Hellman")){
				comp5++;
			}
			else {
				other++;
			}
			if(randomTest.getWeapon().equals("Gravimeter")){
				w3++;
			}
			else if(randomTest.getWeapon().equals("Quartz")){
				w4++;
			}
			else if(randomTest.getWeapon().equals("Beaker")){
				w5++;
			}
			else {
				other++;
			}
			
		}
		assertTrue(comp3>0);
		assertTrue(comp4>0);
		assertTrue(comp5>0);
		assertTrue(w3>0);
		assertTrue(w4>0);
		assertTrue(w5>0);
		assertTrue(other==0);*/	
	}
}
