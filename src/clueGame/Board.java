package clueGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character,String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	public void initialize(){
		rooms = new HashMap<Character, String>();
		board = new BoardCell[numRows][numColumns];
	}
	public void loadRoomConfig(){
		
	}
	public void loadBoardConfig(){
		
	}
	public void calcAdjacencies(){
		
	}
	public void calcTargets(BoardCell cell, int pathLength){
		
	}
	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		boardConfigFile = string;
		roomConfigFile = string2;
		
	}
	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
		return rooms;
	}
	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}
	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return board[i][j];
	}

}
