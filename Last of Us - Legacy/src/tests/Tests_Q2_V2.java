package tests;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import engine.Game;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Weapon;
import model.world.Cell;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Tests_Q2_V2 {

	String characterPath = "model.characters.Character";
	String collectiblePath = "model.collectibles.Collectible";
	String vaccinePath = "model.collectibles.Vaccine";
	String supplyPath = "model.collectibles.Supply";
	String fighterPath = "model.characters.Fighter";
	String defensiveSupplyPath = "model.collectibles.Defensive";
	String empoweringSupplyPath = "model.collectibles.Empowering";

	String gamePath = "engine.Game";
	String medicPath = "model.characters.Medic";
	String explorerPath = "model.characters.Explorer";
	String heroPath = "model.characters.Hero";

	String gameActionExceptionPath = "exceptions.GameActionException";
	String invalidTargetExceptionPath = "exceptions.InvalidTargetException";
	String movementExceptionPath = "exceptions.MovementException";
	String noAvailableResourcesExceptionPath = "exceptions.NoAvailableResourcesException";
	String notEnoughActionsExceptionPath = "exceptions.NotEnoughActionsException";

	String zombiePath = "model.characters.Zombie";

	@Test
	public void testAttackMethodExists() throws ClassNotFoundException {
		
		testMethodExistsInClass(Class.forName(heroPath), "attack",int.class, int.class, true);
	}
	

	
	 @Test 
	public void testNotEnoughActionsExceptionThrown() throws Exception{
	
		 try {
		Constructor<?> fighterConstructor;
		fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
		
		//new instance of fighter object
		Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
	
		
		Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
		Method setActionPoints = Hero.class.getMethod("setActionsAvailable", int.class);
	
		int availableActions = 0;
		setActionPoints.invoke(createdFighter, availableActions);	

		attack.invoke(createdFighter, 1, 3); // fails
		fail("Should have thrown NotEnoughActions");
		 }
		 catch (NoSuchMethodException e) {
				fail("Hero class should have attack(int x, int y) method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(notEnoughActionsExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to attack a particular cell when NotEnoughActionsException should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a NotEnoughActionsException class");
				}

			}

	
	}
	 @Test 
	public void testInvalidTargetExceptionThrownIfOutsideBorderCase1() throws Exception{
	
		 try {
		Constructor<?> fighterConstructor;
		fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
		
		//new instance of fighter object
		Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
	
		
		Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
		int x = 15;
		int y = 15;
		
		attack.invoke(createdFighter, x, y); // fails
		fail("Should have thrown InvalidTargetException");
		 }
		 catch (NoSuchMethodException e) {
				fail("Hero class should have attack(int x, int y) method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(invalidTargetExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to attack a cell outside the borders InvalidTargetException should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be an InvalidTargetException class");
				}

			}
	 }

	 
	 @Test 
	public void testInvalidTargetExceptionThrownIfOutsideBorderCase2() throws Exception{
	
		 try {
		Constructor<?> fighterConstructor;
		fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
		
		//new instance of fighter object
		Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
	
		
		Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
		int x = -1;
		int y = -2;
	
		attack.invoke(createdFighter, x, y); // fails
		fail("Should have thrown InvalidTargetException");
		 }
		 catch (NoSuchMethodException e) {
				fail("Hero class should have attack(int x, int y) method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(invalidTargetExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to attack a cell outside the borders InvalidTargetException should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be an InvalidTargetException class");
				}

			}
	 }
	
	 
	 
	 
	 @Test 
	public void testInvalidTargetExceptionThrownIfTrapCell() throws Exception{
	
		 try {
		Constructor<?> fighterConstructor;
		fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
		
		//new instance of fighter object
		Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
	
		
		Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
		int x = 1;
		int y = 3;
		Cell c = new TrapCell();
		Game.map[x][y] = c;
	
		attack.invoke(createdFighter, 1, 3); // fails
		fail("Should have thrown InvalidTargetException");
		 }
		 catch (NoSuchMethodException e) {
				fail("Hero class should have attack(int x, int y) method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(invalidTargetExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to attack a non character cell when InvalidTargetException should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a InvalidTargetException class");
				}

			}
	}
	 
	 @Test 
	public void testInvalidTargetExceptionThrownIfCollectibleCell() throws Exception{
	
		 try {
		Constructor<?> fighterConstructor;
		fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
		
		//new instance of fighter object
		Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
	
		
		Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
		int x = 1;
		int y = 3;
		Cell c = new CollectibleCell(new Supply());
		Game.map[x][y] = c;
	
		attack.invoke(createdFighter, 1, 3); // fails
		fail("Should have thrown InvalidTargetException");
		 }
		 catch (NoSuchMethodException e) {
				fail("Hero class should have attack(int x, int y) method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(invalidTargetExceptionPath).equals(e.getCause().getClass())))
						fail("Trying to attack a non character cell when InvalidTargetException should be thrown");

				} catch (ClassNotFoundException e1) {
					 
					fail("There should be a InvalidTargetException class");
				}

			}
	}
	 
	 @Test 
	public void testInvalidTargetExceptionThrownIfCharacterIsHero() throws Exception{
	
		 try {
		Constructor<?> fighterConstructor;
		fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
		
		//new instance of fighter object
		Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
		
		Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
		int x = 1;
		int y = 3;
		Cell c = new CharacterCell(new Medic("Medic1", 100, 50, 4));
		Game.map[x][y] = c;
	
		attack.invoke(createdFighter, 1, 3); // fails
		fail("Should have thrown InvalidTargetException");
		 }
		 catch (NoSuchMethodException e) {
				fail("Hero class should have attack(int x, int y) method");
			} catch (InvocationTargetException e) {
				try {
					if (!(Class.forName(invalidTargetExceptionPath).equals(e.getCause().getClass())))
						fail("You can only attack zombies.");

				} catch (ClassNotFoundException e1) { 
					fail("There should be a InvalidTargetException class");
				}

			}

	
	}
	 
	 @Test
	 public void testNoAvailableResourcesThrownIfSupplyIsNotDefensive() throws Exception {
		 try {
			 	ArrayList<Supply> supplies = null;
				Constructor<?> fighterConstructor;
				fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
				
				//new instance of fighter object
				Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
			
				Constructor<?> empoweringSupplyConstructor;
				empoweringSupplyConstructor = Class.forName(empoweringSupplyPath).getConstructor();				
				Object createdSupply = empoweringSupplyConstructor.newInstance();
				
				
				Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
				Method pick = createdSupply.getClass().getMethod("pickUp", Hero.class);
				Method getSupplies = createdFighter.getClass().getMethod("getSupplyInventory");
				
				pick.invoke(createdSupply, createdFighter);		
				supplies = (ArrayList<Supply>) getSupplies.invoke(createdFighter);
				int dmg = 0;
				int x = 1;
				int y = 3;
	
				
				Cell c = new CharacterCell(new Zombie());
				Game.map[x][y] = c;

				
				attack.invoke(createdFighter, 1, 3); // fails
				fail("Should have thrown NoAvailableResourcesException");
				 }
				 catch (NoSuchMethodException e) {
						fail("Hero class should have attack(int x, int y) method");
					} catch (InvocationTargetException e) {
						try {
							if (!(Class.forName(noAvailableResourcesExceptionPath).equals(e.getCause().getClass())))
								fail("You need to have Defensive supply in your inventory to perform this tpe of attack.");

						} catch (ClassNotFoundException e1) {
							 
							fail("There should be a NoAvailableResourcesException class");
						}

					}
	 }
	 
	 @Test 
		public void  testSupplyIsDefensiveConsumed() throws Exception{
			Constructor<?> fighterConstructor;
			fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
			
			//new instance of fighter object
			Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
		
			Constructor<?> defensiveSupplyConstructor;
			defensiveSupplyConstructor = Class.forName(defensiveSupplyPath).getConstructor(Weapon.class);
			
			Object createdSupply = defensiveSupplyConstructor.newInstance(Weapon.MELEE);
			
			
			Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
			Method pick = createdSupply.getClass().getMethod("pickUp", Hero.class);
			Method getSupplies = createdFighter.getClass().getMethod("getSupplyInventory");
	
			
			pick.invoke(createdSupply, createdFighter);		
			ArrayList<Supply> supplies = (ArrayList<Supply>) getSupplies.invoke(createdFighter);
			int size = supplies.size();
			int dmg = 10;
			int x = 1;
			int y = 3;
			Cell c = new CharacterCell(new Zombie());
			Game.map[x][y] = c;
			
			attack.invoke(createdFighter, x, y); 
			assertTrue("Defensive supply must be removed from inventory", supplies.size()==size-1);
	 }

	 
	 @Test 
		public void  testSupplyIsDefensiveArmgunToPerformAttack() throws Exception{
		
			 
			Constructor<?> fighterConstructor;
			fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
			
			//new instance of fighter object
			Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
		
			Constructor<?> defensiveSupplyConstructor;
			defensiveSupplyConstructor = Class.forName(defensiveSupplyPath).getConstructor(Weapon.class);
			
			Object createdSupply = defensiveSupplyConstructor.newInstance(Weapon.ARMGUN);
			
			
			Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
			Method pick = createdSupply.getClass().getMethod("pickUp", Hero.class);
			Method getSupplies = createdFighter.getClass().getMethod("getSupplyInventory");
			
			pick.invoke(createdSupply, createdFighter);		
			ArrayList<Supply> supplies = (ArrayList<Supply>) getSupplies.invoke(createdFighter);
			int dmg = 20;
			int x = 1;
			int y = 3;
			Cell c = new CharacterCell(new Zombie());
			Game.map[x][y] = c;
			int zHP = ((CharacterCell)c).getCharacter().getCurrentHp();
			
			attack.invoke(createdFighter, x, y); // fails
			int newzHP = ((CharacterCell)c).getCharacter().getCurrentHp();
			assertEquals("Defensive Supply with weapon type ARMGUN or RANGED should have a damage effect of 20 on the zombie's HP", zHP - dmg, newzHP);
	 }
	 
	 @Test 
		public void  testSupplyIsDefensiveRangedToPerformAttack() throws Exception{
			Constructor<?> fighterConstructor;
			fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
			
			//new instance of fighter object
			Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
		
			Constructor<?> defensiveSupplyConstructor;
			defensiveSupplyConstructor = Class.forName(defensiveSupplyPath).getConstructor(Weapon.class);
			
			Object createdSupply = defensiveSupplyConstructor.newInstance(Weapon.RANGED);
			
			
			Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
			Method pick = createdSupply.getClass().getMethod("pickUp", Hero.class);
			Method getSupplies = createdFighter.getClass().getMethod("getSupplyInventory");
			
			pick.invoke(createdSupply, createdFighter);		
			ArrayList<Supply> supplies = (ArrayList<Supply>) getSupplies.invoke(createdFighter);
			int dmg = 20;
			int x = 1;
			int y = 3;
			Cell c = new CharacterCell(new Zombie());
			Game.map[x][y] = c;
			int zHP = ((CharacterCell)c).getCharacter().getCurrentHp();
			
			attack.invoke(createdFighter, x, y); // fails
			int newzHP = ((CharacterCell)c).getCharacter().getCurrentHp();
			assertEquals("Defensive Supply with weapon type ARMGUN or RANGED should have a damage effect of 20 on the zombie's HP", zHP - dmg, newzHP);
	 }
	 
	 @Test 
		public void  testSupplyIsDefensiveMeleeToPerformAttack() throws Exception{ 
			Constructor<?> fighterConstructor;
			fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
			
			//new instance of fighter object
			Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
		
			Constructor<?> defensiveSupplyConstructor;
			defensiveSupplyConstructor = Class.forName(defensiveSupplyPath).getConstructor(Weapon.class);
			
			Object createdSupply = defensiveSupplyConstructor.newInstance(Weapon.MELEE);
			
			
			Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
			Method pick = createdSupply.getClass().getMethod("pickUp", Hero.class);
			Method getSupplies = createdFighter.getClass().getMethod("getSupplyInventory");
			
			pick.invoke(createdSupply, createdFighter);		
			ArrayList<Supply> supplies = (ArrayList<Supply>) getSupplies.invoke(createdFighter);
			int dmg = 10;
			int x = 1;
			int y = 3;
			Cell c = new CharacterCell(new Zombie());
			Game.map[x][y] = c;
			int zHP = ((CharacterCell)c).getCharacter().getCurrentHp();
			
			attack.invoke(createdFighter, x, y); // fails
			int newzHP = ((CharacterCell)c).getCharacter().getCurrentHp();
			assertEquals("Defensive Supply with weapon type MELEE should have a damage effect of 10 on the zombie's HP", zHP - dmg, newzHP);
	 }
		 
	 
	 @Test 
		public void  testSupplyIsDefensiveShieldedEffect() throws Exception{
			Constructor<?> fighterConstructor;
			fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
			
			//new instance of fighter object
			Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
		
			Constructor<?> defensiveSupplyConstructor;
			defensiveSupplyConstructor = Class.forName(defensiveSupplyPath).getConstructor(Weapon.class);
			
			Object createdSupply = defensiveSupplyConstructor.newInstance(Weapon.MELEE);
			
			
			Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
			Method pick = createdSupply.getClass().getMethod("pickUp", Hero.class);
			Method getSupplies = createdFighter.getClass().getMethod("getSupplyInventory");
			Method isShielded = createdSupply.getClass().getMethod("isShield");
			
			pick.invoke(createdSupply, createdFighter);		
			ArrayList<Supply> supplies = (ArrayList<Supply>) getSupplies.invoke(createdFighter);
			int dmg = 10;
			int x = 1;
			int y = 3;
			Cell c = new CharacterCell(new Zombie());
			Game.map[x][y] = c;
			
			attack.invoke(createdFighter, x, y); // fails
			boolean shield = (boolean) isShielded.invoke(createdSupply);
			assertTrue("Defensive Supply shield effect should be activated in this attack", shield);
	 }
	  
	 @Test 
	public void testAvailableActionsUsedAfterAttack() throws Exception{
		Constructor<?> fighterConstructor;
		fighterConstructor = Class.forName(fighterPath).getConstructor(String.class, int.class, int.class, int.class);
		
		//new instance of fighter object
		Object createdFighter = fighterConstructor.newInstance("Fighter1", 100, 50, 4);
		Constructor<?> defensiveSupplyConstructor;
		defensiveSupplyConstructor = Class.forName(defensiveSupplyPath).getConstructor(Weapon.class);
		
		Object createdSupply = defensiveSupplyConstructor.newInstance(Weapon.MELEE);
		
		Method attack = createdFighter.getClass().getMethod("attack", int.class, int.class) ;
		Method getActionPoints = Hero.class.getMethod("getActionsAvailable");
		Method getSupplies = createdFighter.getClass().getMethod("getSupplyInventory");
		Method isShielded = createdSupply.getClass().getMethod("isShield");
		Method pick = createdSupply.getClass().getMethod("pickUp", Hero.class);

		pick.invoke(createdSupply, createdFighter);		
		ArrayList<Supply> supplies = (ArrayList<Supply>) getSupplies.invoke(createdFighter);
		int dmg = 10;
		int x = 1;
		int y = 3;
		Cell c = new CharacterCell(new Zombie());
		Game.map[x][y] = c;
		int oldAvailableActions =(int) getActionPoints.invoke(createdFighter);

		
		attack.invoke(createdFighter, 1, 3); // fails
		int newAvailableActions =(int) getActionPoints.invoke(createdFighter);
		assertEquals("This type of attack requires one action point to be performed", oldAvailableActions, newAvailableActions + 1);
		 }
	 
	 
		private void testMethodExistsInClass(Class aClass, String methodName, Class inputType1, Class inputType2,
				boolean writeVariable) {
			Method m = null;
			boolean found = true;
			try {
				m = aClass.getDeclaredMethod(methodName, inputType1, inputType2);
			} catch (NoSuchMethodException e) {
				found = false;
			}
			assertTrue(aClass.getSimpleName() + " class should have " + methodName +"(int x, int y) method"  , found);

			assertTrue("Incorrect return type for " + methodName + " method in " + aClass.getSimpleName() + ".",
					m.getReturnType().equals(Void.TYPE));
			
		}
		

		
		private Object createGame() {
			Constructor<?> gameConstructor;
			try {
				gameConstructor = Class.forName(gamePath).getConstructor();
				Object createdGame = gameConstructor.newInstance();
				return createdGame;
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				fail(e.getClass() +" occurred");
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				fail(e.getClass() +" occurred");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				fail(e.getClass() +" occurred");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				fail(e.getClass() +" occurred");
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				fail(e.getClass() +" occurred");
			}

			return null;
		}
		
		
}
