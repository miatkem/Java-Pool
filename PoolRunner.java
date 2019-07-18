

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PoolRunner{
 PoolGame game;
 public static void main(String[] args) {
  new PoolRunner();
 }
 
 public PoolRunner()
 {
  game = new PoolGame();
  JFrame frame = new JFrame();
 
  frame.setResizable(false);
  frame.setSize(288,402);
  frame.setVisible(true);
  frame.setLocationRelativeTo(null);
  frame.add(game.getGraphics());
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  game.start();
 }

}
