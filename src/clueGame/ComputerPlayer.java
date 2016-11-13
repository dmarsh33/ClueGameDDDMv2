package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{
	private int row, col;
	Board board;
	public ComputerPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
		board = Board.getInstance();
		this.row = row;
		this.col = col;
	}
	public BoardCell pickLocation(Set<BoardCell> targets, BoardCell location){
		
		for(BoardCell b: targets){
			if(b.isDoorway() && b.getInitial()!=location.getInitial()){
				return b;
			}
		}
		ArrayList<BoardCell> tar = new ArrayList<BoardCell>(targets);
		Collections.shuffle(tar);
		System.out.println(targets.size());
		return tar.get(0);
	}
	public void makeAccusation(){
		
	}
	public Solution createSuggestion(Set<Card> notSeen, String room){
		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> weapon = new ArrayList<Card>();
		String person, tool;
		for(Card c: notSeen){
			if(c.getType()== CardType.PERSON){
				people.add(c);
			}
			else if(c.getType()== CardType.WEAPON){
				weapon.add(c);
			}
		}
		Collections.shuffle(people);
		Collections.shuffle(weapon);
		person = people.get(0).getCardName();
		tool = weapon.get(0).getCardName();
		Solution guess = new Solution(person, tool, room);
		return guess;
	}
	
	@Override
	public void makeMove(int r, int c){
		BoardCell newCell = pickLocation(board.getTargets(), board.getCellAt(r, c)); //pick location
		board.getPlayersList().get(board.getPlayersList().size() - 1).setLocation(newCell.getRow(), newCell.getColumn());
		//in room?
			//set last visited
			//make suggestion
	}
}
