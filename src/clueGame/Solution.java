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

	public boolean equals(Solution test){
		if(this.person.equals(test.person) && this.weapon.equals(test.weapon) && this.room.equals(test.room)){
			return true;
		}
		else{
			return false;
		}
	}
}
