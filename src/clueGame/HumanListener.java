package clueGame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.JOptionPane;

public class HumanListener implements MouseListener{ //follows first column in flow chart
	private Board board;
	private GuessDialog guessPanel;
	private String returnedCard = "";
	private String newGuess = "";
	public void mousePressed (MouseEvent event) {}
	public void mouseReleased (MouseEvent event) {}
	public void mouseEntered (MouseEvent event) {}
	public void mouseExited (MouseEvent event) {}
	public void mouseClicked (MouseEvent event) {
		board = board.getInstance();
		Set<BoardCell> targets = board.getTargets();
		BoardCell newLocation = null;
		if(!board.getHumanPlayerStatus()){ //is it the human's turn?
			for(BoardCell c : targets){	 //is it a valid target?
				if(c.containsClick(event.getY() - 50, event.getX())){
					newLocation = c;
					break;
				}
			}
			if(newLocation == null){
				JOptionPane.showMessageDialog(null, "That is not a target!"); //if not, return error
			}
			else{ //if it is, 
				board.getCurrentPlayer().makeMove(newLocation.getRow(), newLocation.getColumn()); //update location
				board.setHumanPlayerStatus(true); //reset flag
				for(BoardCell c: board.getTargets()){ //remove highlights
					c.setHighlighted(false);
				}
				board.repaint(); //repaint
				if(!board.getCurrentPlayer().getPlayerRoom().equals("Sidewalk")){
					//display guess dialog box
					guessPanel = new GuessDialog();
					guessPanel.setVisible(true);
					returnedCard = guessPanel.getReturnedCard();
					newGuess = guessPanel.getNewGuess();
				}
				//will check if it is in a room, display dialog, and handle suggestion
			}
		}
	}
	
	public String getNewGuess(){
		return newGuess;
	}
	
	public String getReturnedCard(){
		return returnedCard;
	}
}
