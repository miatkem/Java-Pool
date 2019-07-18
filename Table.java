import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Point;

public class Table {
 PoolBall[] balls = new PoolBall[16];
 Point[] holes = {new Point(5,5),new Point(RIGHTWALL-5,5),
                  new Point(0,(BOTTOMWALL/2)),new Point(RIGHTWALL,(BOTTOMWALL/2)),
                  new Point(5,BOTTOMWALL-5),new Point(RIGHTWALL-5,BOTTOMWALL-5)};
 PoolStick stick = new PoolStick();
 static int LEFTWALL=0;
 static int RIGHTWALL=282;
 static int TOPWALL=0;
 static int BOTTOMWALL=373;
 double friction=.98;
 
 public Table()
 {
  setup(10);
 }
 
 public void tick()
 {
     
     if(gameOver())
         setup(10);
  for(int i=0; i<balls.length; i++)
      if(!balls[i].inPocket) {
   balls[i].tick(this);
  }
  for(int i=0; i<balls.length-1; i++) {
    for(int j=i+1; j<balls.length; j++){
     if(!balls[i].inPocket&&!balls[j].inPocket&&checkCollision(balls[i],balls[j]))
     {
      double distance = Math.sqrt(((balls[i].xPosition-balls[j].xPosition) *(balls[i].xPosition-balls[j].xPosition)) 
        + ((balls[i].yPosition-balls[j].yPosition) * (balls[i].yPosition-balls[j].yPosition)));
      double overlap = (distance - 20)/2;
      balls[i].xPosition-= overlap * (balls[i].xPosition-balls[j].xPosition) / distance;
      balls[i].yPosition-= overlap * (balls[i].yPosition-balls[j].yPosition) / distance;
      balls[j].xPosition+= overlap * (balls[i].xPosition-balls[j].xPosition) / distance;
      balls[j].yPosition+= overlap * (balls[i].yPosition-balls[j].yPosition) / distance;
      
      
      //collision done here
      double[] newSpeeds=collide(balls[i],balls[j]);
      balls[i].setVel(newSpeeds[0], newSpeeds[1]);
      balls[j].setVel(newSpeeds[2], newSpeeds[3]);
     }
   }
  }
  for(int i=0; i<balls.length; i++)
  {
      if(inHole(balls[i])){
          balls[i].inPocket=true;
          balls[i].xPosition=-100;
          balls[i].xSpeed=0;
          balls[i].ySpeed=0;
      }
  }
  stick.tick(this);
 }
 
 public void paint(Graphics g)
 {
  Graphics2D g2 = (Graphics2D) g;
  g2.setColor(new Color(10,108,3));
  g2.fillRect(0, 0, RIGHTWALL, BOTTOMWALL);
  g2.setColor(Color.black);
  
  g2.fill(new Ellipse2D.Double(-15,-15,40,40));
  g2.fill(new Ellipse2D.Double(RIGHTWALL-25,-15,40,40));
  g2.fill(new Ellipse2D.Double(-20,(BOTTOMWALL/2)-20,40,40));
  g2.fill(new Ellipse2D.Double((RIGHTWALL-20),(BOTTOMWALL/2)-20,40,40));
  g2.fill(new Ellipse2D.Double(-15,BOTTOMWALL-25,40,40));
  g2.fill(new Ellipse2D.Double(RIGHTWALL-25,BOTTOMWALL-25,40,40));
  
  for(int i=0; i<balls.length; i++)
      balls[i].paint(g);
  if(!ballsMoving())
      stick.paint(g);
         
 }
 
 public boolean inHole(PoolBall ball)
 {
   for(int i =0; i < holes.length; i++){
       double distance = ((ball.xPosition-holes[i].getX()) *(ball.xPosition-holes[i].getX())) 
                         + ((ball.yPosition-holes[i].getY()) * (ball.yPosition-holes[i].getY()));
       if(distance<400)
           return true;
   }
   return false;
 }
 
 public boolean checkCollision(PoolBall ballOne ,PoolBall ballTwo)
 {
     
  if (ballOne.xPosition + ballOne.radius + ballTwo.radius > ballTwo.xPosition 
   && ballOne.xPosition < ballTwo.xPosition + ballOne.radius + ballTwo.radius
   && ballOne.yPosition + ballOne.radius + ballTwo.radius > ballTwo.yPosition 
   && ballOne.yPosition < ballTwo.yPosition + ballOne.radius + ballTwo.radius)
  {
   //Do more precise check 
   double distance =((ballOne.xPosition-ballTwo.xPosition) *(ballOne.xPosition-ballTwo.xPosition)) 
   + ((ballOne.yPosition-ballTwo.yPosition) * (ballOne.yPosition-ballTwo.yPosition));
   if (distance <= (ballOne.radius + ballTwo.radius)*(ballOne.radius + ballTwo.radius))
   {
    return true;
   
   }    
  }
  return false;
 }
 
 public double[] collide(PoolBall ballOne, PoolBall ballTwo)
 {
  double distance = Math.sqrt(((ballOne.xPosition-ballTwo.xPosition) *(ballOne.xPosition-ballTwo.xPosition)) 
   + ((ballOne.yPosition-ballTwo.yPosition) * (ballOne.yPosition-ballTwo.yPosition)));
  double nx =  (ballOne.xPosition-ballTwo.xPosition)/distance;
  double ny =  (ballOne.yPosition-ballTwo.yPosition)/distance;
  
  double tx = -ny;
  double ty = nx;
  
  double dpTan1= ballOne.xSpeed * tx +  ballOne.ySpeed*ty;
  double dpTan2= ballTwo.xSpeed * tx +  ballTwo.ySpeed*ty;
  
  double dpNorm1= ballOne.xSpeed * nx +  ballOne.ySpeed*ny;
  double dpNorm2= ballTwo.xSpeed * nx +  ballTwo.ySpeed*ny;
  
  double[] speeds = {(tx*dpTan1+nx*dpNorm2), (ty*dpTan1+ny*dpNorm2), (tx*dpTan2+nx*dpNorm1), (ty*dpTan2+ny*dpNorm1)};
  return speeds; 
  
 }
 

 public void setup(int ballRadius)
 {
  int width = RIGHTWALL-LEFTWALL;
  int height = BOTTOMWALL-TOPWALL;
  double center = width/2.0;
  double dot = height/3.0;
  balls[0]=new PoolBall(center, dot*2, 0, ballRadius);
  balls[1]=new PoolBall(center, dot, 1, ballRadius);
  balls[2]=new PoolBall(center+ballRadius, dot-(ballRadius*2), 2, ballRadius);
  balls[9]=new PoolBall(center-ballRadius, dot-(ballRadius*2), 9, ballRadius);
  balls[8]=new PoolBall(center, dot-(ballRadius*4), 8, ballRadius);
  
  balls[5]=new PoolBall(center-(ballRadius*4), dot-(ballRadius*8), 5, ballRadius);
  balls[12]=new PoolBall(center+(ballRadius*4), dot-(ballRadius*8), 12, ballRadius);
  
  ArrayList<Integer> leftOverBalls = randomizeBalls();
  balls[leftOverBalls.get(0)] = new PoolBall(center-(ballRadius*2), dot-(ballRadius*4), leftOverBalls.get(0), ballRadius);
  balls[leftOverBalls.get(1)] = new PoolBall(center+(ballRadius*2), dot-(ballRadius*4), leftOverBalls.get(1), ballRadius);
  
  balls[leftOverBalls.get(2)] = new PoolBall(center+ballRadius, dot-(ballRadius*6), leftOverBalls.get(2), ballRadius);
  balls[leftOverBalls.get(3)] = new PoolBall(center+(ballRadius*3), dot-(ballRadius*6), leftOverBalls.get(3), ballRadius);
  balls[leftOverBalls.get(4)] = new PoolBall(center-ballRadius, dot-(ballRadius*6), leftOverBalls.get(4), ballRadius);
  balls[leftOverBalls.get(5)] = new PoolBall(center-(ballRadius*3), dot-(ballRadius*6), leftOverBalls.get(5), ballRadius);
  
  balls[leftOverBalls.get(6)] = new PoolBall(center, dot-(ballRadius*8), leftOverBalls.get(6), ballRadius);
  balls[leftOverBalls.get(7)] = new PoolBall(center+(ballRadius*2), dot-(ballRadius*8), leftOverBalls.get(7), ballRadius);
  balls[leftOverBalls.get(8)] = new PoolBall(center-(ballRadius*2), dot-(ballRadius*8), leftOverBalls.get(8), ballRadius);
  
  
  
 }
 
 private ArrayList<Integer> randomizeBalls()
 {
  ArrayList<Integer> ballList = new ArrayList<Integer>(Arrays.asList(3,4,6,7,10,11,13,14,15));
  ArrayList<Integer> randomBalls=new ArrayList<Integer>();
  while(ballList.size()>0)
  {
   int randomIndex=(int)(Math.random()*ballList.size());
   randomBalls.add(ballList.remove(randomIndex));
  }
  return randomBalls;
 }
 
 public double getFriction()
 {
  return friction;
 }

 public boolean ballsMoving() {
  for(int i=0; i<balls.length; i++) {
   if(balls[i].isMoving())
    return true;
  }
  return false;
 }

 public PoolBall[] getBalls() {
  return balls;
 }
 
 public boolean gameOver()
 {
     return balls[0].inPocket;
 }
 
 public PoolStick getStick()
 {
  return stick;
 }
}
