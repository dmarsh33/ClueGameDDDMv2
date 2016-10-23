package clueGame;

public class Solution {
	public String person;
	public String weapon;
	public String room;
	public Solution(String person, String weapon, String room) {
		super();
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	
	public String getPerson() {
		return person;
	}

	public String getWeapon() {
		return weapon;
	}

	public String getRoom() {
		return room;
	}

	public boolean equals(Solution test){
		if(this.person.equals(test.getPerson()) && this.weapon.equals(test.getWeapon()) && this.room.equals(test.getRoom())){
			return true;
		}
		else{
			return false;
		}
	}
}
