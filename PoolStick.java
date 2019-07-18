import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;

public class PoolStick implements MouseListener, MouseMotionListener 
{
 double xPower, yPower;
 double xMouse, yMouse;
 double xCue, yCue;
 boolean onScreen, release;
 
 public void paint(Graphics g)
 {
  if(onScreen)
  {
   Graphics2D g2 = (Graphics2D) g;
   g2.setColor(Color.BLACK);
   g2.draw(new Line2D.Double(xMouse, yMouse, xCue, yCue));
  }
 }
 public void tick(Table table) 
 {
  xCue=table.getBalls()[0].getX();
  yCue=table.getBalls()[0].getY();
  if(release)
  {
   
   table.getBalls()[0].shoot(xPower,yPower);
   release=false;
  }
 }
 
 @Override
 public void mouseClicked(MouseEvent e) {
  release=true;
 }

 @Override
 public void mouseEntered(MouseEvent e) {
  onScreen=true;
 }

 @Override
 public void mouseExited(MouseEvent e) {
  onScreen=false;
 }

 @Override
 public void mousePressed(MouseEvent e) {
 }

 @Override
 public void mouseReleased(MouseEvent e) {
  release=true;
 }

 @Override
 public void mouseDragged(MouseEvent e) {
 }

 @Override
 public void mouseMoved(MouseEvent e) {
  xMouse=e.getX();
  yMouse=e.getY();
  xPower=(xCue-xMouse)/5;
  yPower=(yCue-yMouse)/5;
 }
 
 
}
