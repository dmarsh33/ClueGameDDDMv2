package clueGame;

import java.lang.reflect.Field;
import java.awt.Color;
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
	private Map<String, Player> people;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Map<String,Card> deck;
	private String boardConfigFile;
	private String roomConfigFile;
	private String weaponConfigFile;
	private String playerConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// ctor is private to ensure only one can be created
	private Board() {
		numRows = 0;
		numColumns = 0;
		rooms = new HashMap<Character, String>();
		board = new BoardCell[numRows][numColumns];
		people = new HashMap<String, Player>();
		deck = new HashMap<String, Card>();
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayerConfig();
			loadWeaponConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calcAdjacencies();
		
	}
	public void setConfigFiles(String string, String string2, String string3, String string4) {
		boardConfigFile = string;
		roomConfigFile = string2;
		playerConfigFile = string3;
		weaponConfigFile = string4;
		
	}
	public void loadPlayerConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(playerConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()){
			String line = in.nextLine();
			String [] things = line.split(", ");
			String name = things[0];
			String color = things[1];
			String row = things[2];
			String col = things[3];
			Color c = convertColor(color);
			int r = Integer.parseInt(row);
			int column = Integer.parseInt(col);
			Player player = new Player(name, r, column, c);
			people.put(name, player);
			Card card = new Card(name, CardType.PERSON);
			deck.put(name, card);
		}
	
	}
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
	    Color color; 
	    try {     
	        // We can use reflection to convert the string to a color
	        Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
	        color = (Color)field.get(null); 
	    } catch (Exception e) {  
	        color = null; // Not defined  
	    }
	    return color;
	}

	public void loadWeaponConfig() throws FileNotFoundException{
		FileReader reader = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()){
			String name = in.nextLine();
			Card card = new Card(name, CardType.WEAPON);
			deck.put(name, card);
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
			if(dataArray[2].substring(1).equalsIgnoreCase("card")){
				Card card = new Card(dataArray[1].substring(1), CardType.ROOM);
				deck.put(dataArray[1].substring(1), card);
			}
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
	
	public Map<Character, String> getLegend() {
		return rooms;
	}
	public Map<String, Player> getPlayers(){
		return people;
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
	
	public void selectAnswer(){
		
	}
	public Card handleSuggestion(){
		return null;
	}
	public boolean checkAccusation(Solution accusation){
		return false;
	}
	public Map<String, Card> getDeck(){
		return null;
	}

}
