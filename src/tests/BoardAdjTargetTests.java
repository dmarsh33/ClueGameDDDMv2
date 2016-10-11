package tests;

import java.util.Set;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("data/LayoutJLRG.csv", "data/LegendJLRG.txt");		
		board.initialize();
	}
	
	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 23);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(8, 16);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(17, 21);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(20, 3);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(8, 2);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(17, 14);
		assertEquals(0, testList.size());
	}
	
	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(2, 4);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(1, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(1, 18)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(13, 20);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(14, 20)));
		//TEST DOORWAY UP
		testList = board.getAdjList(16, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		
	}

	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(8, 22);
		assertTrue(testList.contains(board.getCellAt(8, 23)));
		assertTrue(testList.contains(board.getCellAt(9, 22)));
		assertTrue(testList.contains(board.getCellAt(8, 21)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 2);
		assertTrue(testList.contains(board.getCellAt(4, 2)));
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertEquals(2, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(4, 18);
		assertTrue(testList.contains(board.getCellAt(4, 19)));
		assertTrue(testList.contains(board.getCellAt(3, 18)));
		assertTrue(testList.contains(board.getCellAt(4, 17)));
		assertEquals(3, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(7, 3);
		assertTrue(testList.contains(board.getCellAt(8, 3)));
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertTrue(testList.contains(board.getCellAt(7, 4)));
		assertTrue(testList.contains(board.getCellAt(7, 2)));
		assertEquals(4, testList.size());
		}
	
	// Test a variety of walkway scenarios
	// These tests are GOLD on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, just two walkway pieces
		Set<BoardCell> testList = board.getAdjList(0, 7);
		assertTrue(testList.contains(board.getCellAt(0, 6)));
		assertTrue(testList.contains(board.getCellAt(1, 7)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, three walkway pieces
		testList = board.getAdjList(14, 0);
		assertTrue(testList.contains(board.getCellAt(13, 0)));
		assertTrue(testList.contains(board.getCellAt(14, 1)));
		assertTrue(testList.contains(board.getCellAt(15, 0)));
		assertEquals(3, testList.size());

		// Test between two rooms, walkways right and left
		testList = board.getAdjList(3, 22);
		assertTrue(testList.contains(board.getCellAt(3, 21)));
		assertTrue(testList.contains(board.getCellAt(3, 23)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(15,15);
		assertTrue(testList.contains(board.getCellAt(14, 15)));
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		assertTrue(testList.contains(board.getCellAt(15, 16)));
		assertTrue(testList.contains(board.getCellAt(16, 15)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(23, 15);
		assertTrue(testList.contains(board.getCellAt(22, 15)));
		assertTrue(testList.contains(board.getCellAt(23, 16)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(9, 23);
		assertTrue(testList.contains(board.getCellAt(8, 23)));
		assertTrue(testList.contains(board.getCellAt(9, 22)));
		assertEquals(2, testList.size());

		// Test on walkway next to  door that is not in the needed
		// direction to enter
		testList = board.getAdjList(3, 19);
		assertTrue(testList.contains(board.getCellAt(3, 20)));
		assertTrue(testList.contains(board.getCellAt(3, 18)));
		assertEquals(2, testList.size());
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT GREEN on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(23, 16, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 16)));
		assertTrue(targets.contains(board.getCellAt(23, 15)));	
		
		board.calcTargets(7, 0, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 0)));
		assertTrue(targets.contains(board.getCellAt(8, 0)));	
		assertTrue(targets.contains(board.getCellAt(7, 1)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(23, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 15)));
		assertTrue(targets.contains(board.getCellAt(21, 16)));
		
		board.calcTargets(7, 0, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 1)));
		assertTrue(targets.contains(board.getCellAt(8, 1)));	
		assertTrue(targets.contains(board.getCellAt(7, 2)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(23, 16, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 15)));
		assertTrue(targets.contains(board.getCellAt(21, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		assertTrue(targets.contains(board.getCellAt(22, 15)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(7, 0, 4);
		targets= board.getTargets();
		for(BoardCell c:targets){
			System.out.println(c.getRow()+" "+c.getColumn());
		}
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(6, 3)));
		assertTrue(targets.contains(board.getCellAt(5, 2)));	
		assertTrue(targets.contains(board.getCellAt(8, 3)));	
		assertTrue(targets.contains(board.getCellAt(7, 4)));	
		assertTrue(targets.contains(board.getCellAt(8, 1)));
		assertTrue(targets.contains(board.getCellAt(7, 2)));
		assertTrue(targets.contains(board.getCellAt(6, 1)));
	}
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(23, 16, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 16)));
		assertTrue(targets.contains(board.getCellAt(18, 17)));	
		assertTrue(targets.contains(board.getCellAt(18, 15)));	
		assertTrue(targets.contains(board.getCellAt(19, 14)));	
		assertTrue(targets.contains(board.getCellAt(20, 15)));	
		assertTrue(targets.contains(board.getCellAt(22, 15)));	
		assertTrue(targets.contains(board.getCellAt(21, 16)));	
		assertTrue(targets.contains(board.getCellAt(19, 16)));
	}
		
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(18, 16, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		// directly right (can't go left 2 steps)
		assertTrue(targets.contains(board.getCellAt(18, 18)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(16, 16)));
		assertTrue(targets.contains(board.getCellAt(20, 16)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		assertTrue(targets.contains(board.getCellAt(19, 15)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(21, 7, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		// test adj.s
		assertTrue(targets.contains(board.getCellAt(18, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 8)));
		// doorway
		assertTrue(targets.contains(board.getCellAt(20, 6)));	
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(13, 3, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 3)));
		// Take two steps
		board.calcTargets(13, 3, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 2)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));
		assertTrue(targets.contains(board.getCellAt(14, 4)));
	}
	

}
