package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueDialog extends JDialog {
	Board board;
	JCheckBox p;
	ArrayList<JCheckBox> peopleBoxes = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> weaponBoxes = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> roomBoxes = new ArrayList<JCheckBox>();
	JComboBox<String> personGuess = new JComboBox<String>();
	JComboBox<String> weaponGuess = new JComboBox<String>();
	JComboBox<String> roomGuess = new JComboBox<String>();
	
	public ClueDialog(){
		board = Board.getInstance();
		setTitle("Detective Notes");
		setSize(500,600);
		setLayout(new GridLayout(3, 2));
		for(Card person : board.getDeckPeople()){
			p = new JCheckBox();
			p.setText(person.getCardName());
			peopleBoxes.add(p);
			personGuess.addItem(person.getCardName());
		}
		for(Card weapon : board.getDeckWeapons()){
			JCheckBox w = new JCheckBox();
			w.setText(weapon.getCardName());
			weaponBoxes.add(w);
			weaponGuess.addItem(weapon.getCardName());
		}
		for(Card room : board.getDeckRooms()){
			JCheckBox r = new JCheckBox();
			r.setText(room.getCardName());
			roomBoxes.add(r);
			roomGuess.addItem(room.getCardName());
		}
		
		JPanel peoplePanel = new JPanel();
		JPanel weaponPanel = new JPanel();
		JPanel roomPanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(3, 2));
		weaponPanel.setLayout(new GridLayout(3, 2));
		roomPanel.setLayout(new GridLayout(0, 2));
		
		for(JCheckBox peopleBox : peopleBoxes){
			
			peoplePanel.add(peopleBox);
		}
		for(JCheckBox weaponBox : weaponBoxes){
			weaponPanel.add(weaponBox);
		}
		for(JCheckBox roomBox : roomBoxes){
			roomPanel.add(roomBox);
		}
		
		peoplePanel.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		weaponPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		roomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		
		
		JPanel peopleGuessPanel = new JPanel();
		JPanel weaponGuessPanel = new JPanel();
		JPanel roomGuessPanel = new JPanel();
		peopleGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		weaponGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		roomGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		
		peopleGuessPanel.add(personGuess);
		weaponGuessPanel.add(weaponGuess);
		roomGuessPanel.add(roomGuess);
		
		add(peoplePanel);
		add(peopleGuessPanel);
		add(roomPanel);
		add(roomGuessPanel);
		add(weaponPanel);
		add(weaponGuessPanel);
	}
	
	
}
