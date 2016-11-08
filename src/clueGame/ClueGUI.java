package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class ClueGUI extends JFrame{
	Board board;
	//private ClueDialog detectiveNotes;
	public ClueGUI(){
		board = Board.getInstance();
		board.setConfigFiles("data/boardLayout2.csv", "data/layout.txt", "data/people.txt", "data/weapons.txt");		
		board.initialize();
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
		add(createMyCardsPanel(), BorderLayout.EAST);
		JMenuBar menuButton = new JMenuBar();
		setJMenuBar(menuButton);
		menuButton.add(createFileMenu());
		
		
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
	
	
	
	public static void main(String[] args){
		ClueGUI clue = new ClueGUI();
		clue.setVisible(true); 
		JOptionPane.showMessageDialog(clue, "You are the human", "Welcome to Clue!!", JOptionPane.INFORMATION_MESSAGE);
		
	}
}
