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
		//creating a test hand of cards the player has not seen
		Set<Card> notSeen = new HashSet<Card>();
		notSeen.add(human);
		notSeen.add(room1);
		notSeen.add(weapon1);
		//This gets the room that the player is currently in
		Character r = board.getCellAt(15, 21).getInitial();
		Map<Character, String> rooms = board.getLegend();
		String room = rooms.get(r);
		//tests to see if the suggestion room is the same room the player is in for two rooms
		Solution correctRoom = test.createSuggestion(notSeen, room);
		assertTrue(correctRoom.getRoom().equals("Brown"));
		ComputerPlayer test2 = new ComputerPlayer("CPW", 3, 6, Color.red);
		r = board.getCellAt(3, 6).getInitial();
		rooms = board.getLegend();
		room = rooms.get(r);
		Solution correctRoom2 = test2.createSuggestion(notSeen, room);
		assertTrue(correctRoom2.getRoom().equals("Hill"));
		//Tests if only one weapon or person is not seen, it is selected as a suggestion
		Solution onlyOneLeft = test2.createSuggestion(notSeen, room);
		assertTrue(onlyOneLeft.getPerson().equals("Human") && onlyOneLeft.getWeapon().equals("Acid"));
		//Tests if the suggestion is randomly chosen when more than one card is not seen of each type
		notSeen.add(computer1);
		notSeen.add(computer2);
		notSeen.add(weapon2);
		notSeen.add(weapon3);
		int human = 0;
		int comp1 = 0;
		int comp2 = 0;
		int w1= 0;
		int w2 = 0;
		int w3 = 0;
		int other = 0;
		for(int i = 1; i < 100; i++){
			Solution randomTest = test2.createSuggestion(notSeen, room);
			if(randomTest.getPerson().equals("Human")){
				human++;
			}
			else if(randomTest.getPerson().equals("Rader")){
				comp1++;
			}
			else if(randomTest.getPerson().equals("CPW")){
				comp2++;
			}
			else {
				other++;
			}
			if(randomTest.getWeapon().equals("Acid")){
				w1++;
			}
			else if(randomTest.getWeapon().equals("Hammer")){
				w2++;
			}
			else if(randomTest.getWeapon().equals("Laser")){
				w3++;
			}
			else {
				other++;
			}
			
		}
		assertTrue(human>0);
		assertTrue(comp1>0);
		assertTrue(comp2>0);
		assertTrue(w1>0);
		assertTrue(w2>0);
		assertTrue(w3>0);
		assertTrue(other==0);	
	}
	
	@Test
	//This test checks the disproveSuggestion method if Player
	public void disproveSuggestionTest(){
		ComputerPlayer test = new ComputerPlayer("Rader", 15, 21, Color.red);
		Set<Card> hand = new  HashSet<Card>();
		hand.add(human);
		hand.add(room1);
		hand.add(weapon1);
		//Tests if that the method will return the one card that the player can disprove if they do no have any of the other cards
		Solution guess = new Solution("Human", "Hill", "Gravimeter");
		assertEquals(test.disproveSuggestion(guess, hand).getCardName(), "Human");
		//Tests that the card will be randomly selected to be shown if the player has multiple cards in common
		guess = new Solution("Human", "Alderson", "Acid");
		int room= 0;
		int weapon = 0;
		int person = 0;
		int other = 0;
		for(int i = 1; i < 100; i++){
			Card c =  test.disproveSuggestion(guess, hand);
			if(c.getCardName().equalsIgnoreCase("Human")){
				person++;
			}
			else if(c.getCardName().equalsIgnoreCase("Alderson")){
				room++;
			}
			else if(c.getCardName().equalsIgnoreCase("Acid")){
				weapon++;
			}
			else {
				other++;
			}
			
		}
		assertTrue(room>0);
		assertTrue(weapon>0);
		assertTrue(person>0);
		assertTrue(other==0);	
		//Returns null if player has no cards in common with guess
		guess = new Solution("Sava", "Hill", "Gravimeter");
		assertEquals(test.disproveSuggestion(guess, hand), null);
	}
}
