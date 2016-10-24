package clueGame;

import java.awt.Color;
import java.util.Set;

public class Player {
	private String playerName;
	protected int row;
	protected int col;
	private Color color;
	
	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Color getColor() {
		return color;
	}

	public Player(String playerName, int row, int col, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.col = col;
		this.color = color;
	}

	public Card disproveSuggestion(Solution suggestion, Set<Card> hand){
		return null;
		
	}
	public boolean equals(Player p){
		if(this.playerName.equalsIgnoreCase(p.getPlayerName())){
			return true;
		}
		else
			return false;
	}
}
