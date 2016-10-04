package experiment;

public class BoardCell {
	private int row;
	private int column;
	
	public BoardCell(int row, int column) {
		super();
		this.setRow(row);
		this.setColumn(column);
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
	
}
