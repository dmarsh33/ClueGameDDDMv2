package experiment;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;

	public IntBoard(int rows, int columns) {
		super();
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				BoardCell cell = new BoardCell(i,j);
				grid[i][j] = cell;
			}
		}

		return;
	}
	
	public BoardCell getCell(int r, int c){
		BoardCell cell = grid[r][c];
		return cell;
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		Set<BoardCell> list = new HashSet<BoardCell>();
		return list;
	}
	
	public void calcAdjacencies(BoardCell[][] grid) {
		Set<BoardCell> adjacencies = new HashSet<BoardCell>();
		return;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		return;
	}
	
	public Set<BoardCell> getTargets() {
		Set<BoardCell> targets = new HashSet<BoardCell>();
		return targets;
	}
	
	public Set<BoardCell> getAdjList() {
		Set<BoardCell> adjacentList = new HashSet<BoardCell>();
		return adjacentList;
	}
	
	
	
}
