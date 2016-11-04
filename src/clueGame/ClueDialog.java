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
	ArrayList<JCheckBox> peopleBoxes = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> weaponBoxes = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> roomBoxes = new ArrayList<JCheckBox>();
	JComboBox<String> personGuess = new JComboBox<String>();
	JComboBox<String> weaponGuess = new JComboBox<String>();
	JComboBox<String> roomGuess = new JComboBox<String>();
	
	public ClueDialog(ArrayList<Card> deckPeople, ArrayList<Card> deckWeapons, ArrayList<Card> deckRooms){
		setSize(500,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLayout(deckPeople, deckWeapons, deckRooms);
	}
	
	public void createLayout(ArrayList<Card> deckPeople, ArrayList<Card> deckWeapons, ArrayList<Card> deckRooms){
		for(Card person : deckPeople){
			JCheckBox p = new JCheckBox();
			p.setText(person.getCardName());
			peopleBoxes.add(p);
			personGuess.addItem(person.getCardName());
		}
		for(Card weapon : deckWeapons){
			JCheckBox w = new JCheckBox();
			w.setText(weapon.getCardName());
			peopleBoxes.add(w);
			personGuess.addItem(weapon.getCardName());
		}
		for(Card room : deckRooms){
			JCheckBox r = new JCheckBox();
			r.setText(room.getCardName());
			peopleBoxes.add(r);
			personGuess.addItem(room.getCardName());
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
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
		
		panel.add(peoplePanel);
		panel.add(peopleGuessPanel);
		panel.add(roomPanel);
		panel.add(roomGuessPanel);
		panel.add(weaponPanel);
		panel.add(weaponGuessPanel);
		
	}
}
