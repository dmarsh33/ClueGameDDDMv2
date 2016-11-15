package clueGame;

import java.awt.Color;
import java.util.*;

public class ComputerPlayer extends Player{
	private BoardCell lastVisited = null;
	private boolean noneDisproved = false; 
	private Solution accusation = null;
	private int row, col;
	//ClueGUI gui = null;
	Board board;
	public ComputerPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
		board = Board.getInstance();
		//gui = ClueGUI.getInstance();
		this.row = row;
		this.col = col;
	}
	public BoardCell pickLocation(Set<BoardCell> targets, BoardCell location){ //--------------------------------------------------------
		for(BoardCell b: targets){
			if(b.isDoorway() && b.getInitial()!=lastVisited.getInitial()){
				return b;
			}
		}
		ArrayList<BoardCell> tar = new ArrayList<BoardCell>(targets);
		Collections.shuffle(tar);
		return tar.get(0);
	}
	public Solution makeAccusation(){
		return accusation;
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
		//can they make an accusation?
		if(noneDisproved){
			makeAccusation();
			return;
		}
		//no --> seenmove
		else{
			BoardCell newCell = pickLocation(board.getTargets(), board.getCellAt(r, c)); //pick location
			board.getPlayersList().get(board.getPlayersList().size() - 1).setLocation(newCell.getRow(), newCell.getColumn());
		}
		//in room?
		if(board.getCellAt(row, col).isDoorway()){
			//set last visited
			lastVisited = board.getCellAt(row, col);
			
			ArrayList<Card> dealt = board.getDealtCards();
			Set<Card> notSeen = new HashSet<Card>();
			for(Card ca : dealt){
				if(!seen.contains(ca)){
					notSeen.add(ca);
				}
			}
			Solution guess =createSuggestion(notSeen, lastVisited.getRoomName());
			String guessText = guess.getPerson() + ", " + guess.getWeapon() + ", " + guess.getRoom();
			//gui.guessBox.setText(guessText);
			//other players disprove
			Card disproving = board.handleSuggestion(board.getPlayersList(), guess);
			if(disproving == null){
				boolean hasCard = false;
				for(Card s : hand){
					if(s.getCardName().equals(board.getCellAt(row, col).getRoomName())){
						hasCard = true;
					}
				}
				if(!hasCard){
					noneDisproved = true;
					accusation = guess;
				}
			}
			else{
				//gui.resultBox.setText(disproving.getCardName());
			}
		}
			
		//update control panel with guess and resule
		//move suggested person to room
		//let all players know which card was shown
		//set flag if no one can disprove
	}
}
