package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

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
	private Board() {
		numRows = 0;
		numColumns = 0;
		rooms = new HashMap<Character, String>();
		board = new BoardCell[numRows][numColumns];
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		try {
			loadRoomConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			loadBoardConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	// Need to increase exception handling. Such as incorrect number of commas 
	public void loadRoomConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()){
			String dataLine = in.nextLine();
			String[] dataArray = dataLine.split(",");
			switch(dataArray[2]){
			case " Card": // Account for space
				break;
			case " Other": // Account for space
				break;
			default:
				throw new BadConfigFormatException("Improper Legend format.");
			}
			rooms.put(dataArray[0].charAt(0), dataArray[1].substring(1)); // substring is required to account for a space after the comma
		}
	}
	
	public void loadBoardConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		
		ArrayList<String[]> rows = new ArrayList<String[]>();
		while (in.hasNextLine()){
			String dataLine = in.nextLine();
			String[] dataArray = dataLine.split(",");
			rows.add(dataArray);
			if(numColumns == 0){
				numColumns = rows.get(0).length;
			} else if (numColumns != dataArray.length){
				throw new BadConfigFormatException("Incorrect number of columns.");
			}
		}
		
		numRows = rows.size();
		board = new BoardCell[numRows][numColumns];
		DoorDirection dir;
		for(int i=0; i<numRows; i++){
			for(int j=0; j<numColumns; j++){
				if(rows.get(i)[j].length() > 1){
//					System.out.println(rows.get(i)[j].charAt(1));
					switch(rows.get(i)[j].charAt(1)){
						case 'U':
							dir = DoorDirection.UP;
							break;
						case 'D':
							dir = DoorDirection.DOWN;
							break;
						case 'L':
							dir = DoorDirection.LEFT;
							break;
						case 'R':
							dir = DoorDirection.RIGHT;
							break;
						default:
							dir = DoorDirection.NONE;
					}
				} else {
					dir = DoorDirection.NONE;
				}
				if(rooms.containsKey(rows.get(i)[j].charAt(0))){
					board[i][j] = new BoardCell(i,j,rows.get(i)[j].charAt(0),dir);
				} else {
					throw new BadConfigFormatException("Invalid room type");
				}
				
			}
		}
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
