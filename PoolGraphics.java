import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PoolGraphics extends JPanel{
 Table pooltable;
 
 public PoolGraphics(Table tab)
 {
  pooltable=tab;
  this.setSize(282, 373);
  this.setBackground(Color.WHITE);
  this.setFocusable(false); 
  addMouseMotionListener(pooltable.getStick());
  addMouseListener(pooltable.getStick());
 }
 
 @Override
 public void paintComponent(Graphics g)
 { 
  super.paintComponent(g);
  BufferedImage buffer = new BufferedImage(282, 373, 3);
  pooltable.paint(buffer.getGraphics()); 
  g.drawImage(buffer, 0,0,this);
 }
 
 public void updateGraphics()
 {
  repaint();
 }
}
