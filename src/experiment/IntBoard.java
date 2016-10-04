package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	private int ROWS, COLUMNS;

	public IntBoard(int rows, int columns) {
		super();
		grid = new BoardCell[rows][columns];
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				BoardCell cell = new BoardCell(i,j);
				grid[i][j] = cell;
			}
		}
		ROWS = rows;
		COLUMNS = columns;
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		calcAdjacencies(grid);
		
		return;
	}
	
	public BoardCell getCell(int r, int c){
		BoardCell cell = grid[r][c];
		return cell;
	}
	// Helper to find adj of single cell
	public Set<BoardCell> generateAdjList(BoardCell cell){
		Set<BoardCell> list = new HashSet<BoardCell>();
		if(cell.getColumn() != (COLUMNS -1)){
			list.add(grid[cell.getRow()][cell.getColumn()+1]);
		}
		if(cell.getColumn() != 0){
			list.add(grid[cell.getRow()][cell.getColumn()-1]);
		}
		if(cell.getRow() != ROWS -1){
			list.add(grid[cell.getRow()+1][cell.getColumn()]);
		}
		if(cell.getRow() != 0){
			list.add(grid[cell.getRow()-1][cell.getColumn()]);
		}
//		System.out.println("Test");
		return list;
	}
	
	public void calcAdjacencies(BoardCell[][] grid) {
		// Fills map
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLUMNS; j++){
//				System.out.println(i+" "+j);
//				System.out.println(grid[i][j].getColumn());
				BoardCell cell = grid[i][j];
				Set<BoardCell> list = generateAdjList(grid[i][j]);
//				System.out.println(list.toString());
				adjMtx.put(cell, list);
				
			}
		}
		return;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		Set<BoardCell> adj = getAdjList(startCell);
		
		for(BoardCell cell:adj){
			if(visited.contains(cell)){
				continue;
			}
			else {
				visited.add(cell);
				if(pathLength == 1){
					targets.add(cell);
				} else {
					System.out.println(pathLength);
					calcTargets(cell, pathLength-1);
				}
				visited.remove(cell);
			}
		}
		return;
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	
	
}
