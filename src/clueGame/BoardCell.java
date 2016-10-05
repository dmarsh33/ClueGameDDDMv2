package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	
	public BoardCell(int row, int column) {
		super();
		this.setRow(row);
		this.setColumn(column);
		this.direction = DoorDirection.NONE;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public boolean isWalkway(){
		return false;
	}
	public boolean isRoom(){
		return false;
	}
	public boolean isDoorway(){
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return direction;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
	
}
