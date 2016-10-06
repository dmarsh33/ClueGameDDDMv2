package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection direction;
	
	public BoardCell(int row, int column, char init, DoorDirection dir) {
		super();
		this.setRow(row);
		this.setColumn(column);
		this.initial = init;
		this.direction = dir;
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
//		System.out.println(direction);
		if(direction == DoorDirection.NONE){
			return false;
		} else{
			return true;
		}
//		return (direction != DoorDirection.NONE);
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

	public char getInitial() {
		return initial;
	}
	
}
