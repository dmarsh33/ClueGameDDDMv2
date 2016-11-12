package clueGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class BoardCell {
	private Board board;
	private int row;
	private int column;
	private static final int LENGTH = 25;
	private static final int HEIGHT = 25;
	private int pixelRow;
	private int pixelCol;
	private char initial;
	private DoorDirection direction;
	private Map<String, Player> players;
	private boolean name = false;
	private String roomName;
	private boolean highlighted = false;
	private Color color;
	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public BoardCell(int row, int column, char init, DoorDirection dir, boolean name, String roomName) {
		super();
		this.setRow(row);
		this.setColumn(column);
		this.initial = init;
		this.direction = dir;
		this.name = name;
		this.roomName = roomName;
		board = Board.getInstance();
	}
	
	public void draw(Graphics g){
		pixelRow = row * LENGTH;
		pixelCol = column * HEIGHT;
		if(isWalkway()){
			if(isHighlighted()){
				color = Color.orange;
				g.setColor(Color.orange);
			}
			else{
				g.setColor(Color.yellow);
				color = Color.yellow;
			}
			g.fillRect(pixelCol, pixelRow, LENGTH, HEIGHT);
			g.setColor(Color.black);
			g.drawRect(pixelCol, pixelRow, LENGTH, HEIGHT);
		}
		else{
			if(isHighlighted()){
				color = Color.orange;
				g.setColor(Color.orange);
			}
			else{
				color = Color.gray;
				g.setColor(Color.gray);
			}
			g.fillRect(pixelCol, pixelRow, LENGTH, HEIGHT);
			if(isDoorway()){
				switch (direction){
				case RIGHT:
					g.setColor(Color.blue);
					g.fillRect(pixelCol + 20, pixelRow, LENGTH, HEIGHT);
					break;
				case LEFT:
					g.setColor(Color.blue);
					g.fillRect(pixelCol, pixelRow, LENGTH - 20, HEIGHT);
					break;
				case UP:
					g.setColor(Color.blue);
					g.fillRect(pixelCol, pixelRow, LENGTH, HEIGHT - 20);
					break;
				case DOWN:
					g.setColor(Color.blue);
					g.fillRect(pixelCol, pixelRow + 20, LENGTH, HEIGHT);
					break;
				}
				
			}
			
			
		}
		if(name){
				g.setColor(Color.black);
				g.drawString(roomName, pixelCol, pixelRow);
			}
	}
	
	public boolean containsClick(int mouseY, int mouseX){
		//Rectangle r = new Rectangle(pixelCol, pixelRow, LENGTH, HEIGHT);
		//if(r.contains(new Point(mouseX, mouseY))){
		//System.out.println(pixelCol + " " + pixelRow + " (" + mouseX + " " + mouseY + ") " + (pixelCol + LENGTH) + " " + (pixelRow + HEIGHT));
		if((mouseX < (pixelCol + LENGTH)) && (mouseX > (pixelCol)) && (mouseY < (pixelRow + HEIGHT)) && (mouseY > (pixelRow))){
			System.out.println("in containsclick");
			return true;
		}
		return false;
	}

	public Color getColor(){
		return color;
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
		return initial == 'W';
	}
	public boolean isRoom(){
		return false;
	}
	public boolean isDoorway(){
		if(direction == DoorDirection.NONE){
			return false;
		} else{
			return true;
		}
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

	public char getInitial() {
		return initial;
	}
	public boolean isName(){
		return name;
	}
}
