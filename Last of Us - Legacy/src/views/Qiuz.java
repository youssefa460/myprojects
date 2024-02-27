package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;

public class Qiuz extends JFrame implements ActionListener{
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton rotate;
    public Qiuz(){
        b1=new JButton("tess");
        b2=new JButton("s");
        b3=new JButton("y");
        b4=new JButton("z");

        this.getContentPane().add(b1,BorderLayout.WEST);
        this.getContentPane().add(b3,BorderLayout.EAST);
        this.getContentPane().add(b2,BorderLayout.NORTH);
        this.getContentPane().add(b4,BorderLayout.SOUTH);

        rotate=new JButton("Rotate");
        rotate.addActionListener(this);
        this.add(rotate,BorderLayout.CENTER);
        this.setSize(700, 700);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource()==rotate){
        String s=b1.getText();
        b1.setText(b4.getText());
        b4.setText(b3.getText());
        b3.setText(b2.getText());
        b2.setText(s);
       }
    }

    public static void main(String [] args){
        new Qiuz();
    }
    
}
