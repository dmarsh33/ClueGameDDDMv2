package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
	}
	public BoardCell pickLocation(Set<BoardCell> targets, BoardCell location){
		for(BoardCell b: targets){
			if(b.isDoorway() && b.getInitial()!=location.getInitial()){
				return b;
			}
		}
		ArrayList<BoardCell> tar = new ArrayList<BoardCell>(targets);
		Collections.shuffle(tar);
		return tar.get(1);
	}
	public void makeAccusation(){
		
	}
	public Solution createSuggestion(Set<Card> hand){
		return null;
	}
}
