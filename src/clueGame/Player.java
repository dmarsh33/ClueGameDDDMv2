package clueGame;

import java.awt.Color;
import java.util.*;

public abstract class Player {
	private String playerName;
	protected int row, col;
	private Color color;
	protected Set<Card> hand, seen;
	
	public abstract void makeMove();
	
	public Set<Card> getHand() {
		return hand;
	}

	public void setHand(Card c) {
		hand.add(c);
	}

	public Set<Card> getSeen() {
		return seen;
	}

	public void setSeen(Card c) {
		seen.add(c);
	}

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
		seen = new HashSet<Card>();
		hand = new HashSet<Card>();
	}

	public Card disproveSuggestion(Solution suggestion, Set<Card> hand){
		ArrayList<Card> inCommon = new ArrayList<Card>();
		for(Card c: hand){
			if(c.getCardName().equalsIgnoreCase(suggestion.getPerson())){
				inCommon.add(c);
			}
			if(c.getCardName().equalsIgnoreCase(suggestion.getWeapon())){
				inCommon.add(c);
			}
			if(c.getCardName().equalsIgnoreCase(suggestion.getRoom())){
				inCommon.add(c);
			}
		}
		Collections.shuffle(inCommon);
		if(inCommon.size() == 0){
			return null;
		}
		else{
			return inCommon.get(0);
		}
	}
	public boolean equals(Player p){
		if(this.playerName.equalsIgnoreCase(p.getPlayerName())){
			return true;
		}
		else
			return false;
	}
}
