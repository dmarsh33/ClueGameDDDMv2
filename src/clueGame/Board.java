package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character,String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
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
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calcAdjacencies();
		
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
		// Fills map
		for(int i = 0; i < numRows; i++){
			for(int j = 0; j < numColumns; j++){
				BoardCell cell = board[i][j];
				Set<BoardCell> list = generateAdjList(i,j);
				adjMatrix.put(cell, list);
			}
		}
		return;
	}
	
	private boolean isInBound(int x, int y){
		if(x >= 0 && x <= numRows-1){
			if(y >= 0 && y <= numColumns-1){
				return true;
			}
		}
		return false;
	}
	
	private boolean isValidAdj(int x, int y, DoorDirection dir){
		if(isInBound(x,y)){
			if(board[x][y].isWalkway()){
				return true;
			} else if (board[x][y].getDoorDirection() == dir){
				return true;
			}
		}
		return false;
	}
	
	private Set<BoardCell> generateAdjList(int x, int y) {
		Set<BoardCell> list = new HashSet<BoardCell>();
//		System.out.println(cell.isWalkway() + " " + cell.getInitial());
		if(!board[x][y].isWalkway() && !board[x][y].isDoorway()){
			return list;
		}
		// Check Right
		if(isValidAdj(x,y+1,DoorDirection.LEFT)){
			list.add(board[x][y+1]);
		}
		// Check Left
		if(isValidAdj(x,y-1, DoorDirection.RIGHT)){
			list.add(board[x][y-1]);
		}
		// Check Down
		if(isValidAdj(x+1,y, DoorDirection.UP)){
			list.add(board[x+1][y]);
		}
		// Check Up
		if(isValidAdj(x-1,y, DoorDirection.DOWN)){
			list.add(board[x-1][y]);
		}
		return list;
	}

	
	
	public void findAllTargets(int x, int y, int pathLength){
		Set<BoardCell> adj = getAdjList(x,y);
		
		for(BoardCell cell:adj){
			if(visited.contains(cell)){
				continue;
			}
			else {
				visited.add(cell);
				if(pathLength == 1 || cell.isDoorway()){
					targets.add(cell);
				} else {
					findAllTargets(cell.getRow(), cell.getColumn(), pathLength-1);
				}
				visited.remove(cell);
			}
		}
		return;
	}
	
	public void calcTargets(int x, int y, int pathLength){
		visited.clear();
		targets.clear();
		visited.add(board[x][y]);
		findAllTargets(x,y,pathLength);
	}
	
	
	
	
	
	
	
/*	private void calculateTargets(int x, int y, int pathLength){
		Set<BoardCell> adj = getAdjList(x,y);
		
		for(BoardCell cell:adj){
			if(visited.contains(cell)){
				continue;
			}
			else {
				visited.add(cell);
				if(pathLength == 1){
					targets.add(cell);
				} else {
					calculateTargets(cell.getRow(), cell.getColumn(), pathLength-1);
				}
				visited.remove(cell);
			}
		}
		return;
	}
	
	public void calcTargets(int x, int y, int pathLength){
		targets.clear();
		visited.clear();
		calculateTargets(x,y,pathLength);
	}*/
	public void setConfigFiles(String string, String string2) {
		boardConfigFile = string;
		roomConfigFile = string2;
		
	}
	public Map<Character, String> getLegend() {
		return rooms;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int i, int j) {
		// TODO Auto-generated method stub
		return board[i][j];
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(board[i][j]);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

}
