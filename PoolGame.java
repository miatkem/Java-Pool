import java.awt.Canvas;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PoolGame {
    //Pool Objects
    Table pooltable;
    PoolGraphics graphics;
    //Swing Components
    JPanel window;
    JPanel infoPanel;
    JButton reset;
    JLabel balls;
    JLabel shots;
    
    public PoolGame()
    {
        //Pool Objects
        pooltable=new Table();
        graphics=new PoolGraphics(pooltable);
        pooltable.tick();
        //Setup Window
        window = new JPanel();
        window.setLayout(new BorderLayout());
        window.setMinimumSize(new Dimension(282, 373));
        //Setup infoPanel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3,1));
        reset = new JButton("Reset Table:");
        reset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                pooltable.setup(10);
            }
        });
        balls = new JLabel("Balls Left:");
        shots = new JLabel("Shots Taken:");
        infoPanel.add(reset);
        infoPanel.add(balls);
        infoPanel.add(shots);
        //add infoPanel and graphics to window
        window.add(BorderLayout.CENTER, graphics);
        window.add(BorderLayout.EAST,infoPanel);
    }
    
    public void start()
    {
        //Game tick thread - runs 10ms sleep
        Thread tick = new Thread() {
            @Override
            public void run() {
                while (true) 
                {
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {e.printStackTrace();}
                    
                    pooltable.tick();
                    updateScores();
                }
            }};
        //Game render thread - runs at top speed
        Thread render = new Thread() {
            @Override
            public void run() {
                while (true) 
                {
                    graphics.repaint();
                }
            }};
        //Start threads
        tick.start();
        render.start();
    }
    
    public void updateScores()
    {
        balls.setText(("Balls Left:" + pooltable.ballsLeft()));
        shots.setText(("Shots Taken:" + pooltable.getShots()));
    }
    
    public JPanel getWindow() { return window; }
}
