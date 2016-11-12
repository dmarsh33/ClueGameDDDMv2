package clueGame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.JOptionPane;

public class HumanListener implements MouseListener{
	private Board board;
	public void mousePressed (MouseEvent event) {}
	public void mouseReleased (MouseEvent event) {}
	public void mouseEntered (MouseEvent event) {}
	public void mouseExited (MouseEvent event) {}
	public void mouseClicked (MouseEvent event) {
		board = board.getInstance();
		Set<BoardCell> targets = board.getTargets();
		BoardCell newLocation = null;
		/*for(BoardCell c: targets){
			if(c.containsClick(event.getX(), event.getY())){
				newLocation = c;
				break;
			}
				
		}*/
		//for(BoardCell c: targets){
			//Color color = c.getColor();
			//if(c.containsClick(event.getX(), event.getY())){
			//	newLocation = c;
			//	break;
			//}
				
		//}
		//Color c = getColor(event.getX(), event.getY());
		//if()
		
		for(int i = 0; i < board.getNumRows(); i++){
			for(int j = 0; j < board.getNumColumns(); j++){
				
				if(board.getCellAt(i, j).containsClick(event.getY(), event.getX())){
					System.out.println(i + " " + j);
					System.out.println(targets.size());
					//for(BoardCell b : targets){
						//System.out.println(b.getRow() + " " + b.getColumn());
					//}
					if(targets.contains(board.getCellAt(i, j))){
						newLocation = board.getCellAt(i, j);
						System.out.println(newLocation.getRow() + " - " + newLocation.getColumn());
						//call move?
						
						break;
					}
				}
			}
		}
		
		if(newLocation == null){
			JOptionPane.showMessageDialog(null, "That is not a target!");
		}
	}
}
