package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{
	private int row;
	private int col;
	public ComputerPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
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
		return tar.get(1);
	}
	public void makeAccusation(){
		
	}
	public Solution createSuggestion(Set<Card> notSeen){
		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> weapon = new ArrayList<Card>();
		String person;
		String tool;
		String room = null;
		for(Card c: notSeen){
			System.out.println(c.getCardName());
			if(c.getType()== CardType.PERSON){
				people.add(c);
			}
			else if(c.getType()== CardType.WEAPON){
				weapon.add(c);
			}
		}
		/*for(Card c: people){
			System.out.println(c.getCardName());
		}
		for(Card c: weapon){
			System.out.println(c.getCardName());
		}*/
		Collections.shuffle(people);
		Collections.shuffle(weapon);
		person = people.get(0).getCardName();
		tool = weapon.get(0).getCardName();
		//PROBLEM WITH GETTING ROOM WITHOUT BOARD!!
		/*r = board.getCellAt(this.row, this.col).getInitial();
		Map<Character, String> rooms = board.getLegend();
		String room = rooms.get(r);*/
		Solution guess = new Solution(person, tool, room);
		return guess;
	}
}
