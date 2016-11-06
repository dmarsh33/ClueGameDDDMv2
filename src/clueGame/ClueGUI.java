package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClueGUI extends JFrame{
	Board board;
	private ClueDialog detectiveNotes;
	public ClueGUI(){
		board = Board.getInstance();
		board.setConfigFiles("data/boardLayout2.csv", "data/layout.txt", "data/people.txt", "data/weapons.txt");		
		board.initialize();
		setSize(650,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout();
	}
	public void createLayout(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,3));
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		JLabel turnLabel = new JLabel("Whose Turn?");
		JTextField turnBox = new JTextField(20);
		turnPanel.add(turnLabel);
		turnPanel.add(turnBox);
		panel.add(turnPanel);
		JButton nextPlayer = new JButton("Next Player");
		panel.add(nextPlayer);
		JButton makeAccusation = new JButton("Make Accusation");
		panel.add(makeAccusation);
		JPanel diePanel = new JPanel();
		diePanel.setLayout(new GridLayout(1,2));
		diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JLabel dieLabel = new JLabel("Roll");
		JTextField dieBox = new JTextField(20);
		diePanel.add(dieLabel);
		diePanel.add(dieBox);
		panel.add(diePanel);
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2,1));
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		JLabel guessLabel = new JLabel("Guess");
		JTextField guessBox = new JTextField(20);
		guessPanel.add(guessLabel);
		guessPanel.add(guessBox);
		panel.add(guessPanel);
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,2));
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		JLabel resultLabel = new JLabel("Response");
		JTextField resultBox = new JTextField(20);
		resultPanel.add(resultLabel);
		resultPanel.add(resultBox);
		panel.add(resultPanel);
		add(panel, BorderLayout.SOUTH);
		add(board, BorderLayout.CENTER);
		
		JButton menuButton = new JButton("Menu");
		add(menuButton, BorderLayout.NORTH);
		menuButton.addActionListener(new ButtonListener());
	}
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			detectiveNotes = new ClueDialog();
			detectiveNotes.setVisible(true);
		}
	}
	
	
	public static void main(String[] args){
		ClueGUI clue = new ClueGUI();
		clue.setVisible(true); 
		
	}
}
