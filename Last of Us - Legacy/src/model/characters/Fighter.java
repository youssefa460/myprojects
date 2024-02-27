package model.characters;

import exceptions.InvalidTargetException;
import exceptions.NoAvailableResourcesException;

public class Fighter extends Hero{

	
	public Fighter(String name,int maxHp, int attackDmg, int maxActions) {
		super( name, maxHp,  attackDmg,  maxActions) ;
		
	}
	public void useSpecial()throws NoAvailableResourcesException, InvalidTargetException{
		super.useSpecial();

		if(super.isSpecialAction()==false){
			super.setSpecialAction(true);
			super.getSupplyInventory().get(0).use(this);
		}

	}

	

	
	
	
	

}
