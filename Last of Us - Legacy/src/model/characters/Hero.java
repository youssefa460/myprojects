package model.characters;

import java.awt.Point;
import java.util.ArrayList;

import model.collectibles.Defensive;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.collectibles.Weapon;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;
import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;

public abstract class Hero extends Character {

	private int actionsAvailable;
	private int maxActions;
	private boolean specialAction;
	private ArrayList<Vaccine> vaccineInventory;
	private ArrayList<Supply> supplyInventory;

	public Hero(String name, int maxHp, int attackDamage, int maxActions) {
		super(name, maxHp, attackDamage);
		this.maxActions = maxActions;
		this.actionsAvailable = maxActions;
		this.supplyInventory = new ArrayList<Supply>();
		this.vaccineInventory = new ArrayList<Vaccine>();
	}

	public int getActionsAvailable() {
		return actionsAvailable;
	}
	public void attack(int x, int y) throws InvalidTargetException, NotEnoughActionsException, NoAvailableResourcesException{
		if(actionsAvailable==0){
			throw new NotEnoughActionsException();
		}
		if(x>14 || y>14 ||x<0||y<0){
			throw new InvalidTargetException();
		}
		if(Game.map[x][y] instanceof CollectibleCell){
			throw new InvalidTargetException();
		}
		if(Game.map[x][y] instanceof TrapCell){
			throw new InvalidTargetException();
		}
		if(Game.map[x][y] instanceof CharacterCell){
			CharacterCell c =(CharacterCell)Game.map[x][y];
			if(c.getCharacter() instanceof Hero){
				throw new InvalidTargetException();
			}
			else{
				boolean flag=false;
				int index=0;
				for(int i=0;i<this.getSupplyInventory().size();i++){
					if(getSupplyInventory().get(i) instanceof Defensive){
						flag=true;
						index=i;
						break;
					}
				}
				if(flag==false){
					throw new NoAvailableResourcesException();
				}
				if(flag==true){
					Zombie z= (Zombie)c.getCharacter();
					actionsAvailable--;
					Defensive d=(Defensive)getSupplyInventory().get(index);
					d.setShield(true);
					this.supplyInventory.remove(d);
					if(d.getWeapon()==Weapon.ARMGUN|| (d.getWeapon()==Weapon.RANGED)){
						
	
						z.setCurrentHp(z.getCurrentHp()-20);
						
					}else{
						z.setCurrentHp(z.getCurrentHp()-10);
					}
					
				}
			}
		}
	}

	public void setActionsAvailable(int actionsAvailable) {
		if (actionsAvailable <= 0)
			this.actionsAvailable = 0;
		else
			this.actionsAvailable = actionsAvailable;
	}

	public boolean isSpecialAction() {
		return specialAction;
	}

	public void setSpecialAction(boolean specialAction) {
		this.specialAction = specialAction;
	}

	public int getMaxActions() {
		return maxActions;
	}

	public ArrayList<Vaccine> getVaccineInventory() {
		return vaccineInventory;
	}

	public ArrayList<Supply> getSupplyInventory() {
		return supplyInventory;
	}

	public void move(Direction d) throws MovementException, NotEnoughActionsException {
		if (actionsAvailable < 1)
			throw new NotEnoughActionsException("You need at least 1 action point in order to move.");
		int tX = getLocation().x;
		int tY = getLocation().y;
		switch (d) {
		case DOWN:
			tX--;
			break;
		case LEFT:
			tY--;
			break;
		case RIGHT:
			tY++;
			break;
		case UP:
			tX++;
			break;
		}
		if (tX < 0 || tY < 0 || tX > Game.map.length - 1 || tY > Game.map.length - 1)
			throw new MovementException("You cannot move outside the borders of the map.");
		if (Game.map[tX][tY] instanceof CharacterCell && ((CharacterCell) Game.map[tX][tY]).getCharacter() != null)
			throw new MovementException("You cannot move to an occuppied cell.");
		else if (Game.map[tX][tY] instanceof CollectibleCell) {
			((CollectibleCell) Game.map[tX][tY]).getCollectible().pickUp(this);
		} else if (Game.map[tX][tY] instanceof TrapCell) {
			this.setCurrentHp(this.getCurrentHp() - ((TrapCell) Game.map[tX][tY]).getTrapDamage());
		}
		Game.map[getLocation().x][getLocation().y] = new CharacterCell(null);
		this.actionsAvailable--;

		if (this.getCurrentHp() ==  0) {
			return;
		}
		Game.map[tX][tY] = new CharacterCell(this);
		setLocation(new Point(tX, tY));
		Game.adjustVisibility(this);
	}

	@Override
	public void attack() throws NotEnoughActionsException, InvalidTargetException {
		if (actionsAvailable < 1)
			throw new NotEnoughActionsException("You need at least 1 action point to be able to attack.");
		if (this.getTarget() == null)
			throw new InvalidTargetException("You should select a target to attack first.");
		if (!checkDistance())
			throw new InvalidTargetException("You are only able to attack adjacent targets.");
		if (this.getTarget() instanceof Hero)
			throw new InvalidTargetException("You can only attack zombies.");
		super.attack();
		if (this instanceof Fighter && (this.isSpecialAction()))
			return;
		actionsAvailable--;
	}

	public void useSpecial() throws NoAvailableResourcesException, InvalidTargetException {
		if (this.getSupplyInventory().size() == 0)
			throw new NoAvailableResourcesException(
					"You need to have at least 1 supply in your inventory to use your special abililty.");
		this.supplyInventory.get(0).use(this);
		this.setSpecialAction(true);
	}

	public boolean checkDistance() {
		Point p1 = getLocation();
		Point p2 = getTarget().getLocation();
		if (Math.abs(p1.x - p2.x) > 1)
			return false;
		else if (Math.abs(p1.y - p2.y) > 1)
			return false;
		return true;
	}

	public void cure() throws NoAvailableResourcesException, InvalidTargetException, NotEnoughActionsException {
		if (this.vaccineInventory.size() == 0)
			throw new NoAvailableResourcesException(
					"You need to have at least 1 vaccine in your inventory to be able to cure zombies.");
		if (this.actionsAvailable < 1)
			throw new NotEnoughActionsException("You need to have at least 1 action point in order to cure a zombie.");
		if (this.getTarget() == null)
			throw new InvalidTargetException("You need to pick a target to cure first.");
		if (!checkDistance())
			throw new InvalidTargetException("You are only able to cure adjacent targets.");
		if (!(this.getTarget() instanceof Zombie))
			throw new InvalidTargetException("You can only cure zombies.");
		this.vaccineInventory.get(0).use(this);
		actionsAvailable--;
	}

}
