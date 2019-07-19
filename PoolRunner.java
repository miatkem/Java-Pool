import javax.swing.JFrame;

public class PoolRunner{
    PoolGame game;
    //MAIN-starts game immediately
    public static void main(String[] args) {
        new PoolRunner();
    }
    
    public PoolRunner()
    {
        game = new PoolGame();
        
        //set up main frame
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(392,402);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.add(game.getWindow());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game.start();
    }
    
}
