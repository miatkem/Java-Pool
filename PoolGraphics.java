import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class PoolGraphics extends JPanel{
    Table pooltable;
    
    public PoolGraphics(Table tab)
    {
        pooltable=tab;
        this.setPreferredSize(new Dimension(282, 373));
        this.setBackground(Color.WHITE);
        this.setFocusable(false); 
        addMouseMotionListener(pooltable.getStick());
        addMouseListener(pooltable.getStick());
    }
    
    @Override
    public void paintComponent(Graphics g)
    { 
        super.paintComponent(g);
        //Use buffered image as a buffer to draw
        BufferedImage buffer = new BufferedImage(282, 373, 3);
        pooltable.paint(buffer.getGraphics());  //pooltable paints to buffer
        g.drawImage(buffer, 0,0,this); //paint buffer to screen
    }
    
    public void updateGraphics()
    {
        repaint();
    }
}
