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
	private static Card raderCard;
	private static Card CPWCard;
	private static Card SavaCard;
	private static Card SniederCard;
	private static Card AldersonCard;
	private static Card StrattonCard;
	private static Card LibraryCard;
	private static Card AcidCard;
	private static Card HammerCard;
	private static Card LaserCard;
	private static Card QuartzCard;
	private static Card BeakerCard;
	@BeforeClass
	public static void setUp(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our config files
		board.setConfigFiles("data/boardLayout.csv", "data/layout.txt", "data/people.txt", "data/weapons.txt");		
		// Initialize will load all config files 
		board.initialize();
		
		human = new Card("Human", CardType.PERSON);
		raderCard = new Card("Rader", CardType.PERSON);
		CPWCard = new Card("CPW", CardType.PERSON);
		SavaCard = new Card("Sava", CardType.PERSON);
		SniederCard = new Card("Snieder", CardType.PERSON);
		AldersonCard = new Card("Alderson", CardType.ROOM);
		AcidCard = new Card("Acid", CardType.WEAPON);
		HammerCard = new Card("Hammer", CardType.WEAPON);
		LaserCard = new Card("Laser", CardType.WEAPON);
		QuartzCard = new Card("Quartz", CardType.WEAPON);
		BeakerCard = new Card("Beaker", CardType.WEAPON);
		StrattonCard = new Card("Stratton", CardType.ROOM);
		LibraryCard = new Card("Library", CardType.ROOM);
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
		Solution testIncorrectWeapon = new Solution(board.getSolution().person, "x", board.getSolution().room);
		assertFalse(board.checkAccusation(testIncorrectWeapon));
		//Tests solution with incorrect person
		Solution testIncorrectPerson = new Solution("x", board.getSolution().weapon, board.getSolution().room);
		assertFalse(board.checkAccusation(testIncorrectPerson));
		//Tests solution with incorrect room
		Solution testIncorrectRoom = new Solution(board.getSolution().person, board.getSolution().weapon, "x");
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
		notSeen.add(AldersonCard);
		notSeen.add(AcidCard);
		//This gets the room that the player is currently in
		Character r = board.getCellAt(15, 21).getInitial();
		Map<Character, String> rooms = board.getLegend();
		String room = rooms.get(r);
		//tests to see if the suggestion room is the same room the player is in for two rooms
		Solution correctRoom = test.createSuggestion(notSeen, room);
		assertTrue(correctRoom.room.equals("Brown"));
		ComputerPlayer test2 = new ComputerPlayer("CPW", 3, 6, Color.red);
		r = board.getCellAt(3, 6).getInitial();
		rooms = board.getLegend();
		room = rooms.get(r);
		Solution correctRoom2 = test2.createSuggestion(notSeen, room);
		assertTrue(correctRoom2.room.equals("Hill"));
		//Tests if only one weapon or person is not seen, it is selected as a suggestion
		Solution onlyOneLeft = test2.createSuggestion(notSeen, room);
		assertTrue(onlyOneLeft.person.equals("Human") && onlyOneLeft.weapon.equals("Acid"));
		//Tests if the suggestion is randomly chosen when more than one card is not seen of each type
		notSeen.add(raderCard);
		notSeen.add(CPWCard);
		notSeen.add(HammerCard);
		notSeen.add(LaserCard);
		int human = 0;
		int comp1 = 0;
		int comp2 = 0;
		int w1= 0;
		int w2 = 0;
		int w3 = 0;
		int other = 0;
		for(int i = 1; i < 100; i++){
			Solution randomTest = test2.createSuggestion(notSeen, room);
			if(randomTest.person.equals("Human")){
				human++;
			}
			else if(randomTest.person.equals("Rader")){
				comp1++;
			}
			else if(randomTest.person.equals("CPW")){
				comp2++;
			}
			else {
				other++;
			}
			if(randomTest.weapon.equals("Acid")){
				w1++;
			}
			else if(randomTest.weapon.equals("Hammer")){
				w2++;
			}
			else if(randomTest.weapon.equals("Laser")){
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
		hand.add(AldersonCard);
		hand.add(AcidCard);
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
	
	@Test
	//
	public void handleSuggestionTest(){
		ComputerPlayer rader= new ComputerPlayer("Rader", 9, 19, Color.red);
		ComputerPlayer CPW = new ComputerPlayer("CPW", 9, 17, Color.magenta);
		Player humanPlayer = new Player("Human", 20,5, Color.blue);
		ComputerPlayer sava = new ComputerPlayer("Sava", 13, 12, Color.green);
		rader.setHand(AcidCard);
		rader.setHand(AldersonCard);
		rader.setHand(human);
		CPW.setHand(HammerCard);
		CPW.setHand(StrattonCard);
		CPW.setHand(raderCard);
		sava.setHand(LaserCard);
		sava.setHand(SavaCard);
		sava.setHand(BeakerCard);
		humanPlayer.setHand(QuartzCard);
		humanPlayer.setHand(LibraryCard);
		humanPlayer.setHand(CPWCard);
		//Rader is accuser - Tests that if no player can disprove suggestion, returns null
		ArrayList<Player> raderAccuser = new ArrayList<Player>();
		raderAccuser.add(CPW);
		raderAccuser.add(sava);
		raderAccuser.add(humanPlayer);
		raderAccuser.add(rader);
		Solution suggestionNull = new Solution("Snieder", "Gravimeter", "Hill");
		assertTrue(board.handleSuggestion(raderAccuser, suggestionNull)==null);
		//Rader is accuser - Tests that only accusing player can disprove the suggestion
		Solution suggestionAccusing = new Solution("Snieder", "Gravimeter", "Alderson");
		assertTrue(board.handleSuggestion(raderAccuser, suggestionAccusing)==null);
		//Rader is accuser - Tests that if the human can disprove it, the card that the human has is returned
		Solution suggestionHuman = new Solution("CPW", "Gravimeter", "Hill");
		assertTrue(board.handleSuggestion(raderAccuser,suggestionHuman).getCardName().equalsIgnoreCase("CPW"));
		//Human is accuser - Tests that returns null if only human can disprove and human is accuser
		ArrayList<Player> humanAccuser = new ArrayList<Player>();
		humanAccuser.add(rader);
		humanAccuser.add(CPW);
		humanAccuser.add(sava);
		humanAccuser.add(humanPlayer);
		Solution suggestionHuman2 = new Solution("Snieder", "Gravimeter", "Library");
		assertTrue(board.handleSuggestion(humanAccuser, suggestionHuman2)==null);
		//Rader is accuser - Tests that if two players can disprove, the first one in the list is the one to show the card
		Solution suggestion2Players = new Solution("Sava", "Hammer", "Alderson");
		assertTrue(board.handleSuggestion(raderAccuser, suggestion2Players).getCardName().equalsIgnoreCase("Hammer"));
		//CPW is accuser - Tests that if human and another player can disprove, the first one returns card
		ArrayList<Player> CPWAccuser = new ArrayList<Player>();
		CPWAccuser.add(sava);
		CPWAccuser.add(humanPlayer);
		CPWAccuser.add(rader);
		CPWAccuser.add(CPW);
		Solution suggestion2Players2 = new Solution("Sava", "Quartz", "Hill");
		assertTrue(board.handleSuggestion(CPWAccuser, suggestion2Players2).getCardName().equalsIgnoreCase("Sava"));
	}
}
