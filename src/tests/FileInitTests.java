package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileInitTests {
	// Test constants
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 24;
	public static final int NUM_COLUMNS = 24;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("LayoutJLRG.csv", "LegendJLRG.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testRooms(){
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		//TODO:pull legend info
		assertEquals("Main Deck", legend.get('M'));
		assertEquals("Medical Bay", legend.get('B'));
		assertEquals("Engine Room", legend.get('E'));
		assertEquals("Officer's Quarters", legend.get('O'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions(){
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());	
	}
	
	@Test
	public void FourDoorDirections() {
		// Test right direction
		BoardCell room = board.getCellAt(21, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		// Test down direction
		room = board.getCellAt(11, 5);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		// Test left direction
		room = board.getCellAt(18, 18);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		// Test up direction
		room = board.getCellAt(3, 8);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		
		// Test that room pieces that aren't doors
		room = board.getCellAt(0, 0);
		assertFalse(room.isDoorway());	
		
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(15, 15);
		assertFalse(cell.isDoorway());		
	}
	
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(13, numDoors);
	}
	
	@Test
	public void testRoomInitials() {
		// TODO: Change initials and coor to match our layout
		// Test all room cells to ensure correct room initials
		assertEquals('K', board.getCellAt(20, 1).getInitial());
		assertEquals('S', board.getCellAt(11, 17).getInitial());
		assertEquals('G', board.getCellAt(19, 18).getInitial());
		assertEquals('C', board.getCellAt(3, 9).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(8, 15).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(11,11).getInitial());
	}
	
}
