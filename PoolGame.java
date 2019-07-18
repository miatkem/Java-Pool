import java.awt.Canvas;
import javax.swing.JPanel;
import java.awt.Component;

public class PoolGame {
 Table pooltable;
 PoolGraphics graphics;
 
 public PoolGame()
 {
  pooltable=new Table();
  graphics=new PoolGraphics(pooltable);
  pooltable.tick();
 }
 
 public void start()
 {
  Thread tick = new Thread() {
  @Override
  public void run() {
   while (true) {
    try {
    sleep(10);
    } catch (InterruptedException e) {e.printStackTrace();}

    pooltable.tick();
      }
  }};
       
       
  Thread render = new Thread() {
  @Override
  public void run() 
  {
   while (true) 
   {
    graphics.repaint();
   }
  }};
  tick.start();
  render.start();
 }

 public JPanel getGraphics() {
  // TODO Auto-generated method stub
  return graphics;
 }
 
}
