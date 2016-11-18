package clueGame;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ClueGUI extends JFrame{
	Board board;
	AccusationDialog accusationPanel;
	boolean humanPlayerFinished = false;
	private Player current = null;
	private int dieRoll;
	private Random die = new Random();
	public JTextField turnBox = new JTextField(20);
	public JTextField dieBox = new JTextField(20);
	public JTextField guessBox =new JTextField(20);
	public JTextField resultBox = new JTextField(20);
	HumanListener humanClick = new HumanListener();
	Set<BoardCell> targets = null;
	
	
	public ClueGUI(){
		board = Board.getInstance();
		board.setConfigFiles("data/boardLayout2.csv", "data/layout.txt", "data/people.txt", "data/weapons.txt");		
		board.initialize();
		current = board.getPlayersList().get(0);
		board.reorderPlayers();
		dieRoll = die.nextInt(6) + 1;
		board.calcTargets(current.getRow(), current.getCol(), dieRoll);
		targets = board.getTargets();
		for(BoardCell c: targets){
			c.setHighlighted(true);
		}
		board.setCurrentPlayer(current);
		board.setHumanPlayerStatus(humanPlayerFinished);
		setSize(800,800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout();
	}
	public void createLayout(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,3));
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		JLabel turnLabel = new JLabel("Whose Turn?");
		turnPanel.add(turnLabel);
		turnPanel.add(turnBox);
		panel.add(turnPanel);
		JButton nextPlayer = new JButton("Next Player");
		panel.add(nextPlayer);
		nextPlayer.addActionListener(new NextPlayerListener());
		turnBox.setText("Human");
		//panel.addMouseListener();
		JButton makeAccusation = new JButton("Make Accusation");
		panel.add(makeAccusation);
		makeAccusation.addActionListener(new MakeAccusationListener());
		JPanel diePanel = new JPanel();
		diePanel.setLayout(new GridLayout(1,2));
		diePanel.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JLabel dieLabel = new JLabel("Roll");
		diePanel.add(dieLabel);
		diePanel.add(dieBox);
		dieBox.setText(String.valueOf(dieRoll));
		panel.add(diePanel);
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2,1));
		guessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		JLabel guessLabel = new JLabel("Guess");
		guessPanel.add(guessLabel);
		guessPanel.add(guessBox);
		panel.add(guessPanel);
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new GridLayout(1,2));
		resultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		JLabel resultLabel = new JLabel("Response");
		resultPanel.add(resultLabel);
		resultPanel.add(resultBox);
		panel.add(resultPanel);
		add(panel, BorderLayout.SOUTH);
		JPanel boardLayout = new JPanel();
		addMouseListener(humanClick);
		add(board, BorderLayout.CENTER);
		board.repaint();
		add(createMyCardsPanel(), BorderLayout.EAST);
		JMenuBar menuButton = new JMenuBar();
		setJMenuBar(menuButton);
		menuButton.add(createFileMenu());
		
		
	}
	
	private class NextPlayerListener implements ActionListener{ //follows second column in flow chart
		public void actionPerformed(ActionEvent e){ //next player button clicked
			humanPlayerFinished = board.getHumanPlayerStatus();//is human finished?
			if(humanPlayerFinished){ // human turn has been finished
				current = board.getPlayersList().get(0);
				board.setCurrentPlayer(current); //rotate to next player
				board.reorderPlayers();
				turnBox.setText(current.getPlayerName()); //set display
				dieRoll = die.nextInt(5) + 1; //roll die
				dieBox.setText(String.valueOf(dieRoll)); //set display
				board.calcTargets(current.getRow(), current.getCol(), dieRoll); //calc targets
				if(current.getPlayerName().equalsIgnoreCase("Human")){ //just changed to humans turn
					if(!board.getCurrentPlayer().getDisprovingCard().equals("")){
						board.getCurrentPlayer().setDisprovingCard(null);
						board.getCurrentPlayer().setSolution("", "", "");
					}
					board.calcTargets(current.getRow(), current.getCol(), dieRoll);
					targets = board.getTargets();
					//guessBox.setText(board.getCurrentPlayer().getGuess());
					//resultBox.setText(board.getCurrentPlayer().getDisprovingCard());
					for(BoardCell c: targets){ //highlight targets
						c.setHighlighted(true);
					}
					humanPlayerFinished = false;
					board.setHumanPlayerStatus(humanPlayerFinished);
					
					//repaint();
				}
				else{
					board.getCurrentPlayer().makeMove(current.getRow(), current.getCol());//call makeMove
					guessBox.setText(board.getCurrentPlayer().getGuess());
					resultBox.setText(board.getCurrentPlayer().getDisprovingCard());
					board.repaint(); //repaint
					//repaint();
				}
			}
			else{ //human has not finished 
				JOptionPane.showMessageDialog(null, "You need to finish your turn!");
				
			}
			repaint();
		}
	}
	
	
	
	private class MakeAccusationListener implements ActionListener{ //follows second column in flow chart
		public void actionPerformed(ActionEvent e){ //next player button clicked
			if(!board.getCurrentPlayer().getPlayerName().equalsIgnoreCase("Human")){
				JOptionPane.showMessageDialog(null, "It is not your turn!");
			}
			else{ //add logic - only at beginning of turn
				if(!board.getHumanPlayerStatus()){
					accusationPanel = new AccusationDialog();
					accusationPanel.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "You already finished your turn! Click next player.");
				}
			}
		}
	}

	private JPanel createMyCardsPanel(){
		JPanel myCards = new JPanel();
		myCards.setLayout(new GridLayout(3,1));
		myCards.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		JTextArea peopleBox = new JTextArea(10, 10);
		peoplePanel.add(peopleBox);
		JPanel roomsPanel = new JPanel();
		roomsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		JTextArea roomsBox = new JTextArea(10, 10);
		roomsPanel.add(roomsBox);
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		JTextArea weaponsBox = new JTextArea(10, 10);
		weaponsPanel.add(weaponsBox);
		Set<Card> humanHand = board.getPlayers().get("Human").getHand();
		for(Card c : humanHand){
			switch (c.getType()){
			case PERSON: 
				peopleBox.append(c.getCardName() + "\n");
				break;
			case WEAPON:
				weaponsBox.append(c.getCardName() + "\n");
				break;
				
			case ROOM: 
				roomsBox.append(c.getCardName() + "\n");
				break;
			}
		}
		
		myCards.add(peoplePanel);
		myCards.add(roomsPanel);
		myCards.add(weaponsPanel);
		return myCards;
	}
	
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotes());
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem(){
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createDetectiveNotes(){
		JMenuItem notes = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				ClueDialog detectiveNotes = new ClueDialog();
				detectiveNotes.setVisible(true);
			}
		}
		notes.addActionListener(new MenuItemListener());
		return notes;
	}
	
	public void paintComponent(Graphics g){
		//txtfieldWhoseTurn.setText(b.getCurrentPlayerName());
		//txtfieldRoll.setText(b.getRollNum());
		System.out.println("paint component gui");
		guessBox.setText(board.getCurrentPlayer().getGuess());
		resultBox.setText(board.getCurrentPlayer().getDisprovingCard());
	}
	
	public void update(){
		Timer t = new Timer(10, new TimerListener());
		t.start();
	}
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			guessBox.setText(board.getCurrentPlayer().getGuess());
			resultBox.setText(board.getCurrentPlayer().getDisprovingCard());
		}
	}
	
	public static void main(String[] args){
		ClueGUI clue = new ClueGUI();
		clue.setVisible(true); 
		JOptionPane.showMessageDialog(clue, "You are the human, and it is your turn! Select a highlighted square to begin. ", "Welcome to Clue!!", JOptionPane.INFORMATION_MESSAGE);
		clue.update();
	}
}
