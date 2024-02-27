package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Fighter;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Frame extends JFrame implements ActionListener{
   private JButton b1;
   private JLabel l;
   private static JPanel p;
   private JButton [] selecthero;
   private static JButton GO;
   private Hero HeroToGo;
   private  JButton [][] Map=new JButton[15][15];
   private JPanel p2;
   private JPanel p3;
   private JLabel l1;
   private JButton cure;
   private JButton Attack;
   private JButton endTrun;
   private JButton SpecialAttack;
   private Zombie myTarget;
   private Hero myTarget2;
   private JButton up;
   private JButton down;
   private JButton left;
   private JButton right;
   private JPanel p4;
   private JButton exit;
   private JButton newGame;

   public Frame(){ 
      ImageIcon icon=new ImageIcon("last11.jpeg");
      b1=new JButton("Start Game");
      b1.setBounds(670, 450,100,50);
      b1.addActionListener(this);
      b1.setFocusable(false);
      b1.setBackground(new Color(123,50,250));
      b1.setOpaque(true);
      b1.setFocusable(false);
      l=new JLabel();
      l.setIcon(icon);
      l.setSize(1920, 1080);
      l.add(b1);
      ImageIcon icon2=new ImageIcon("lasti.png");
        
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setIconImage(icon2.getImage());
      //this.setLayout(null);
      this.setSize(1920, 1080);
      this.setTitle("Last of Us-Legacy");
      this.getContentPane().setBackground(Color.black);
      this.add(l,BorderLayout.CENTER);
        
      this.setVisible(true);
      try{
         Game.loadHeroes("Heroes.csv");
      }catch(IOException e){
         JOptionPane.showMessageDialog(this,"No heroes","Error",JOptionPane.ERROR_MESSAGE);
      }

      p=new JPanel();
      p.setLayout(new GridLayout(2,4));
      
      selecthero =new JButton[8];
      for(int i=0;i<selecthero.length;i++){
         ImageIcon i1=null;
         if(Game.availableHeroes.get(i).getName().equals("Joel Miller")){
            i1=new ImageIcon("rsz_joelmiller2.jpg");
         } else if(Game.availableHeroes.get(i).getName().equals("Ellie Williams")){
            i1=new ImageIcon("rsz_elliewilliams.jpg");
         } else if(Game.availableHeroes.get(i).getName().equals("Tess")){
            i1=new ImageIcon("rsz_tess.jpg");
         } else if(Game.availableHeroes.get(i).getName().equals("Riley Abel")){
            i1=new ImageIcon("rsz_1riley-abel.jpg");
         } else if(Game.availableHeroes.get(i).getName().equals("Tommy Miller")){
            i1=new ImageIcon("rsz_tommy-miller.jpg");
         } else if(Game.availableHeroes.get(i).getName().equals("Bill")){
            i1=new ImageIcon("rsz_bill.jpg");
         }else if(Game.availableHeroes.get(i).getName().equals("David")){
            i1=new ImageIcon("rsz_1david.jpg");
         }else if(Game.availableHeroes.get(i).getName().equals("Henry Burell")){
            i1=new ImageIcon("Henry-Burell.jpeg");
         }
         selecthero[i]=new JButton(Game.availableHeroes.get(i).getName(),i1);
         selecthero[i].addActionListener(this);
         p.add(selecthero[i]); 
      }

      GO=new JButton("Go!");
      GO.addActionListener(this); 
      
      exit=new JButton("Exit");
      exit.addActionListener(this);
      exit.setBounds(670, 500,100,50);

      newGame=new JButton("New Game");
      newGame.addActionListener(this);
      newGame.setBounds(670, 450,100,50);
   }

   public static void main(String []args){
      new Frame();

   }

    @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() ==b1){
        l.setVisible(false);
        this.getContentPane().add(p,BorderLayout.CENTER);
        this.add(GO,BorderLayout.SOUTH);
      }
      for(int i=0;i<selecthero.length;i++){
         if(e.getSource()==selecthero[i]){
            HeroToGo=Game.availableHeroes.get(i);
            String Type="";
            if(Game.availableHeroes.get(i) instanceof Fighter){
               Type="Figther";
            }else if(Game.availableHeroes.get(i) instanceof Explorer){
               Type="Explorer";
            }
            else{
               Type="Medic";
            }
            JOptionPane.showMessageDialog(this,Game.availableHeroes.get(i).getName()+"\n"+"Current HP: "+Game.availableHeroes.get(i).getCurrentHp()+"\n"+"Max Actions: "+Game.availableHeroes.get(i).getMaxActions()+"\n"+"Attack Damge: "+Game.availableHeroes.get(i).getAttackDmg()+"\n"+Type,"Info",JOptionPane.INFORMATION_MESSAGE);
            return;
         }
      }
      
      if(e.getSource()==GO){
         p.setVisible(false);
         GO.setVisible(false); 
         Start(HeroToGo);
         return;
      }
      for(int i=0;i<Map.length;i++){
         for(int j=0;j<Map.length;j++){
            if(e.getSource()==Map[i][j]){
               if(Game.map[i][j] instanceof CharacterCell){
                  CharacterCell c=(CharacterCell)Game.map[i][j];
                  if(c.getCharacter() instanceof Hero){
                     HeroToGo=(Hero) c.getCharacter();
                     l1.setText(info(HeroToGo));
                  } else if(c.getCharacter() instanceof Zombie){
                     myTarget=(Zombie)c.getCharacter();
                     l1.setText("<html><body>"+myTarget.getName()+"<br> CurrentHp"+myTarget.getCurrentHp()+"</body></html>");
                  } else if(HeroToGo instanceof Medic){
                     myTarget2=(Hero)c.getCharacter();
                  }
               }
            }
         }
      }
      if(e.getSource()==endTrun){
         try{
            Game.endTurn();
            showOnMap();
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(NotEnoughActionsException|InvalidTargetException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
         
      }
      if(e.getSource()==SpecialAttack){
         try{
            if(HeroToGo instanceof Medic){
               HeroToGo.setTarget(myTarget2);
            }else{
               HeroToGo.setTarget(myTarget);
            }
            HeroToGo.useSpecial();
            showOnMap();
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(NoAvailableResourcesException|InvalidTargetException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
      }
      if(e.getSource()==Attack){
         try{
            HeroToGo.setTarget(myTarget);
            HeroToGo.attack();
            showOnMap();
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(InvalidTargetException|NotEnoughActionsException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
      }
      if(e.getSource()==cure){
         try{
            HeroToGo.setTarget(myTarget);
            HeroToGo.cure();
            showOnMap();
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(InvalidTargetException|NotEnoughActionsException|NoAvailableResourcesException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
      }

      if(e.getSource()==up){
         try{
            HeroToGo.move(Direction.DOWN);
            showOnMap();
            int x=(int)HeroToGo.getLocation().getX();
            int y=(int)HeroToGo.getLocation().getY();
            if(Game.map[x][y] instanceof TrapCell){
               JOptionPane.showMessageDialog(this,"Its a trap!");
            }
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(MovementException|NotEnoughActionsException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
      }
      if(e.getSource()==down){
         try{
            HeroToGo.move(Direction.UP);
            showOnMap();
            int x=(int)HeroToGo.getLocation().getX();
            int y=(int)HeroToGo.getLocation().getY();
            if(Game.map[x][y] instanceof TrapCell){
               JOptionPane.showMessageDialog(this,"Its a trap!");
            }
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(MovementException|NotEnoughActionsException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
      }
      if(e.getSource()==left){
         try{
            HeroToGo.move(Direction.LEFT);
            showOnMap();
            int x=(int)HeroToGo.getLocation().getX();
            int y=(int)HeroToGo.getLocation().getY();
            if(Game.map[x][y] instanceof TrapCell){
               JOptionPane.showMessageDialog(this,"Its a trap!");
            }
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(MovementException|NotEnoughActionsException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
      }
      if(e.getSource()==right){
         try{
            HeroToGo.move(Direction.RIGHT);
            showOnMap();
            int x=(int)HeroToGo.getLocation().getX();
            int y=(int)HeroToGo.getLocation().getY();
            if(Game.map[x][y] instanceof TrapCell){
               JOptionPane.showMessageDialog(this,"Its a trap!");
            }
            if(Game.checkWin()){
               JOptionPane.showMessageDialog(this, "You did it!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }else if(Game.checkGameOver()){
               JOptionPane.showMessageDialog(this, "Better luck next time!");
               p4.setVisible(false);
               p3.setVisible(false);
               l1.setVisible(false);
               p2.setVisible(false);
               this.add(newGame);
               this.add(exit);
            }
         }catch(MovementException|NotEnoughActionsException e1){
            JOptionPane.showMessageDialog(this,e1.getMessage());
         }
         
      }
      if(e.getSource()==exit){
         this.dispose();
      }
      if(e.getSource()==newGame){
         newGame.setVisible(false);
         exit.setVisible(false);
         p.setVisible(true);
         GO.setVisible(true);
      }
   }

   public void Start(Hero h){
      Game.startGame(h);
      this.getContentPane().setLayout(null);
      p2=new JPanel();
      p2.setLayout(new GridLayout(4, 1));
      p2.setBounds(10, 10, 150, 200);
      p2.setBackground(Color.black);
      this.getContentPane().add(p2);
      
      cure =new JButton("cure", null);
      cure.addActionListener(this);
      p2.add(cure);

      endTrun=new JButton("End Turn", null);
      endTrun.addActionListener(this);
      p2.add(endTrun);

      Attack=new JButton("Attack", null);
      Attack.addActionListener(this);
      p2.add(Attack);

      SpecialAttack=new JButton("Use special", null);
      SpecialAttack.addActionListener(this);
      p2.add(SpecialAttack);
      
      p3=new JPanel();
      p3.setLayout(new GridLayout(15, 15));
      p3.setBounds(300,10,850,850);
      p3.setBackground(Color.black);
      this.getContentPane().add(p3);

      l1=new JLabel(info(h));
      l1.setText(info(h));
      l1.setVisible(true);
      l1.setBounds(10,240,150,150);
      l1.setForeground(Color.white);
      this.getContentPane().add(l1);
      this.getContentPane().setBackground(Color.black);
      for(int i=0;i<Map.length;i++){
         for(int j=0;j<Map.length;j++){
            Map[i][j]=new JButton();
            Map[i][j].addActionListener(this);
            p3.add(Map[i][j]);
         }
         
      }
      p4=new JPanel();
      p4.setBounds(10, 400, 200, 100);
      p4.setBackground(Color.black);
      this.getContentPane().add(p4);

      up=new JButton("Up");
      up.setBounds(100, 10, 15, 15);
      up.addActionListener(this);
      p4.add(up);
      
      down=new JButton("Down");
      down.setBounds(100, 190, 15, 15);
      down.addActionListener(this);
      p4.add(down);

      left=new JButton("Left");
      left.setBounds(100, 190, 15, 15);
      left.addActionListener(this);
      p4.add(left);

      right=new JButton("Right");
      right.setBounds(100, 190, 15, 15);
      right.addActionListener(this);
      p4.add(right);

      showOnMap();
   }
   public String info(Hero h){
      String Type="";
      if(h instanceof Fighter){
         Type="Figther";
      }else if(h instanceof Explorer){
         Type="Explorer";
      }
      else{
         Type="Medic";
      }
      return "<html><body>"+h.getName()+"<br>"+"Current HP: "+h.getCurrentHp()+"<br>"+"Max Actions: "+h.getMaxActions()+"<br>"+"Attack Damge: "+h.getAttackDmg()+"<br>"+"Supplies: "+HeroToGo.getSupplyInventory().size()+"<br>"+"Vaccines: "+HeroToGo.getVaccineInventory().size()+"<br>"+Type+"</body></html>";
      
   }

   public void showOnMap(){
      for(int i=0;i<Map.length;i++){
         for(int j=0;j<Map.length;j++){
            Map[i][j].setIcon(null);
            if (Game.map[i][j].isVisible()==false){
              Map[i][j].setBackground(Color.BLACK);
              Map[i][j].setOpaque(true);
              Map[i][j].setBorderPainted(false);
            }else{
               Map[i][j].setBackground(Color.white);
               Map[i][j].setOpaque(true);
               Map[i][j].setBorderPainted(false);
               if(Game.map[i][j] instanceof CharacterCell){
                  CharacterCell c=(CharacterCell)Game.map[i][j];
                  if(c.getCharacter() instanceof Zombie){
                  Map[i][j].setIcon(new ImageIcon("zombie.jpeg"));
               } else if(c.getCharacter() instanceof Fighter){
                  Map[i][j].setIcon(new ImageIcon("Fighter2.jpeg"));
               }else if(c.getCharacter() instanceof Medic){
                  Map[i][j].setIcon(new ImageIcon("medic2.jpeg"));
               }else if(c.getCharacter() instanceof Explorer){
                  Map[i][j].setIcon(new ImageIcon("explorer2.jpeg"));
               }
            } else if(Game.map[i][j] instanceof CollectibleCell){
               CollectibleCell c=(CollectibleCell)Game.map[i][j];
               if(c.getCollectible() instanceof Supply){
                  Map[i][j].setIcon(new ImageIcon("supply.jpeg"));
               } else if(c.getCollectible() instanceof Vaccine){
                  Map[i][j].setIcon(new ImageIcon("vaccine.jpeg"));
               }
            }
         }
      }
   }
} 
}
    

