import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class PoolBall {
 double xSpeed, ySpeed;
 double xPosition, yPosition;
 double radius;
 int number;
 boolean isCueBall,collided;
 boolean inPocket;
 
 public PoolBall(double xPosition, double yPosition, int number)
 {
  this.xPosition=xPosition;
  this.yPosition=yPosition;
  ySpeed=xSpeed=0;
  this.number=number; //pool ball number
  radius=10.0;
  if(number==0) //0 means cue ball
   isCueBall=true;
  else 
   isCueBall=false;
  inPocket=false;
 }
 
 public PoolBall(double xPosition, double yPosition, int number, double radius)
 {
  
  
  this.xPosition=xPosition;
  this.yPosition=yPosition;
  this.number=number;
  this.radius=radius;
  if(number==0)
  {
   ySpeed=0;
   isCueBall=true;
  }
  else 
   isCueBall=false;
  inPocket=false;
 }
 
 public void tick(Table poolTable)
 {
     if(!inPocket){
         if(Math.abs(xSpeed)<.15)
             xSpeed=0.0;
         if(Math.abs(ySpeed)<.15)
             ySpeed=0.0;
         
         xPosition+=xSpeed;
         yPosition+=ySpeed;
         
         if(xPosition-radius<=Table.LEFTWALL)
         {
             xPosition=Table.LEFTWALL+radius;
             xSpeed*=-1;
         }
         
         if(xPosition+radius>=Table.RIGHTWALL)
         {
             xPosition=Table.RIGHTWALL-radius;
             xSpeed*=-1;
         }
         if(yPosition-radius<=Table.TOPWALL)
         {
             yPosition=Table.TOPWALL+radius;
             ySpeed*=-1;
         }
         
         if(yPosition+radius>=Table.BOTTOMWALL)
         {
             yPosition=Table.BOTTOMWALL-radius;
             ySpeed*=-1;
         }
         xSpeed=xSpeed*poolTable.getFriction();
         ySpeed=ySpeed*poolTable.getFriction();
         
         if(Math.abs(xSpeed)<.15)
             xSpeed=0.0;
         if(Math.abs(ySpeed)<.15)
             ySpeed=0.0;
     }
 }
 
 public int getNumber() 
 {
  return number;
 }

 public boolean isCueBall()
 {
  return isCueBall();
 }
 
 public double getX()
 {
  return xPosition;
 }
 
 public double getY()
 {
  return yPosition;
 }
 
 public double getRadius()
 {
  return radius;
 }
 
 public void paint(Graphics g)
 {
  Graphics2D g2 = (Graphics2D) g;
  switch(number)
  {
   case 0: g2.setColor(Color.WHITE); break;
   case 1: 
   case 9: g2.setColor(Color.YELLOW); break;
   case 2: 
   case 10: g2.setColor(Color.BLUE); break;
   case 3: 
   case 11: g2.setColor(Color.RED); break;
   case 4: 
   case 12: g2.setColor(new Color(128,0,128)); break;
   case 5: 
   case 13: g2.setColor(new Color(255,140,0)); break;
   case 6: 
   case 14: g2.setColor(Color.GREEN); break;
   case 7: 
   case 15: g2.setColor(new Color(128,0,0)); break;
   case 8: g2.setColor(Color.BLACK); break;
  }
  g2.fill(new Ellipse2D.Double(xPosition-radius,yPosition-radius,radius*2,radius*2));
  g2.setColor(Color.BLACK);
  g2.draw(new Ellipse2D.Double(xPosition-radius,yPosition-radius,radius*2,radius*2));
  
 }

 public boolean isMoving() {
  if(Math.abs(xSpeed)>0||Math.abs(ySpeed)>0)
   return true;
  return false;
 }

 public void shoot(double xPow, double yPow) {
  xSpeed+=xPow;
  ySpeed+=yPow;
 }
 
 public void setVel(double xVel,double yVel)
 {
  xSpeed=xVel;
  ySpeed=yVel;
 }
}
