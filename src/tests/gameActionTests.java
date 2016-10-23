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
import clueGame.Solution;

public class gameActionTests {
	private static Board board;
	@BeforeClass
	public static void setUp(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our config files
		board.setConfigFiles("data/boardLayout.csv", "data/layout.txt", "data/people.txt", "data/weapons.txt");		
		// Initialize will load all config files 
		board.initialize();
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
}
