package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class AccusationDialog extends JDialog{
	Board board;
	JComboBox<String> personSuggestion = new JComboBox<String>();
	JComboBox<String> weaponSuggestion = new JComboBox<String>();
	JTextField yourRoom = new JTextField(20);
	JTextField person = new JTextField(20);
	JTextField weapon = new JTextField(20);
	JComboBox<String> roomSuggestion = new JComboBox<String>();
	JButton submitButton = new JButton("Submit");
	JButton cancelButton = new JButton("Cancel");
	
	
	public AccusationDialog(){
		board = Board.getInstance();
		setTitle("Make a Guess");
		setSize(200,200);
		setLayout(new GridLayout(4, 2));
		for(Card person : board.getDeckPeople()){
			personSuggestion.addItem(person.getCardName());
		}
		for(Card weapon : board.getDeckWeapons()){
			weaponSuggestion.addItem(weapon.getCardName());
		}
		for(Card room : board.getDeckRooms()){
			roomSuggestion.addItem(room.getCardName());
		}
		
		yourRoom.setText("Your Room");
		person.setText("Person");
		weapon.setText("Weapon");
	
		
		
		submitButton.addActionListener(new SubmitButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
		
		add(yourRoom);
		add(roomSuggestion);
		add(person);
		add(personSuggestion);
		add(weapon);
		add(weaponSuggestion);
		add(submitButton);
		add(cancelButton);
	}

	
	private class SubmitButtonListener implements ActionListener{ //listens to submit button click
		public void actionPerformed(ActionEvent e){ 
			String guessedPerson = personSuggestion.getSelectedItem().toString();
			String guessedWeapon = weaponSuggestion.getSelectedItem().toString();
			String guessedRoom = roomSuggestion.getSelectedItem().toString();
			
			board.getCurrentPlayer().setSolution(guessedPerson, guessedWeapon, guessedRoom);
			Solution accusation = new Solution(guessedPerson, guessedWeapon, guessedRoom);
			setVisible(false);
			
			if(accusation.equals(board.getSolution())){
				JOptionPane.showMessageDialog(null, "That is correct! You won!");
			}
			else{
				JOptionPane.showMessageDialog(null, "That is incorrect!");
			}
		}
	}
	
	private class CancelButtonListener implements ActionListener{ //listens to submit button click
		public void actionPerformed(ActionEvent e){ 
			setVisible(false);
		}
	}
}
