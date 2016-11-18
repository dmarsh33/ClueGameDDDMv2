package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player{
	public HumanPlayer(String playerName, int row, int col, Color color) {
		super(playerName, row, col, color);
	}

	@Override
	public void makeMove(int r, int c){
		row = r;
		col = c;
	}
}
