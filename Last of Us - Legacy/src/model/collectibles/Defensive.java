package model.collectibles;

public class Defensive extends Supply{

	private boolean shield;
    private	Weapon weapon;

	public Defensive(Weapon weapon)
	{
		this.weapon = weapon;
		shield = false;
	}
	
	public boolean isShield() {
		return shield;
	}
	public void setShield(boolean shield) {
		this.shield = shield;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}
