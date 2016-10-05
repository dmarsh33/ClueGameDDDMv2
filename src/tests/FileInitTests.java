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
	//TODO: modify these
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp(){
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CR_ClueLayout.csv", "CR_ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testRooms(){
		Map<Character, String> legend = board.getLegend();
		assertEquals(LEGEND_SIZE, legend.size());
		//TODO:pull legend info
	}
	
	@Test
	public void testBoardDimensions(){
		
	}
	
}
