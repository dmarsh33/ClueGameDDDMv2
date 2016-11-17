package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class GuessDialog extends JDialog{
	Board board;
	JComboBox<String> personSuggestion = new JComboBox<String>();
	JComboBox<String> weaponSuggestion = new JComboBox<String>();
	JTextField yourRoom = new JTextField(20);
	JTextField person = new JTextField(20);
	JTextField weapon = new JTextField(20);
	JTextField room = new JTextField(20);
	JButton submitButton = new JButton("Submit");
	JButton cancelButton = new JButton("Cancel");
	String returnedCard = "";
	Solution newGuess = null;
	String guessString = "";
	
	public GuessDialog(){
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
		
		yourRoom.setText("Your Room");
		person.setText("Person");
		weapon.setText("Weapon");
		room.setText(board.getPlayersList().get(board.getPlayersList().size() - 1).getPlayerRoom());
		
		
		submitButton.addActionListener(new SubmitButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
		
		add(yourRoom);
		add(room);
		add(person);
		add(personSuggestion);
		add(weapon);
		add(weaponSuggestion);
		add(submitButton);
		add(cancelButton);
	}

	
	public class SubmitButtonListener implements ActionListener{ //listens to submit button click
		public void actionPerformed(ActionEvent e){ 
			String guessedPerson = personSuggestion.getSelectedItem().toString();
			String guessedWeapon = weaponSuggestion.getSelectedItem().toString();
			String guessedRoom = room.getText();
			guessString = guessedPerson + ", " + guessedWeapon + ", " + guessedRoom;
			newGuess = new Solution(guessedPerson, guessedWeapon, guessedRoom);
			board.getCurrentPlayer().setSolution(guessedPerson, guessedWeapon, guessedRoom); //sets player's suggestion and guesstext
			Card c = board.handleSuggestion(board.getPlayersList(), newGuess);
			board.getCurrentPlayer().setDisprovingCard(c);
			//returnedCard = c.getCardName();
			setVisible(false);
			Player guessedPlayer = board.getPlayers().get(guessedPerson);
			guessedPlayer.setLocation(board.getPlayersList().get(board.getPlayersList().size() - 1).getRow(), board.getPlayersList().get(board.getPlayersList().size() - 1).getCol());
		}
	}
	
	public class CancelButtonListener implements ActionListener{ //listens to submit button click
		public void actionPerformed(ActionEvent e){ 
			setVisible(false);
		}
	}
	
	public String getReturnedCard(){
		return returnedCard;
	}
	
	public String getNewGuess(){
		return guessString;
	}
}
