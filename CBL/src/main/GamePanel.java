package main;

import block.BlockManager;
import entity.Player;
import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    // Screen Settings
    private final int originalTileSize = 16; // 16 x 16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // 48 x 48 tile
    private final int maxScreenCol = 24; // 4:3
    private final int maxScreenRow = 18; // ratio
    protected final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    protected final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    private final int fps = 60; // Framerate per second
    private final Color backgroundColor = new Color(71, 35, 14); // Background color

    // SYSTEM
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private BlockManager blockM = new BlockManager(this);
    private KeyHandler keyHandler = new KeyHandler(this);
    private Player player = new Player(this, keyHandler);
    private UI ui = new UI(this);
    private Thread gameThread;

    // GAME STATE
    protected int gameState;
    protected final int playState = 1;
    protected final int pauseState = 2;

    /**
     * GamePanel class CONSTRUCTOR. 
     * 
     */
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Create the Panel
        this.setBackground(backgroundColor);
        this.setDoubleBuffered(true); // Improving game's rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setupGame();
    }

    public void setupGame() {

        gameState = playState;
    }

    public void startGameThread() {

        gameThread = new Thread(this); // Instantiating the thread
        gameThread.start(); // Starting the thread, calling automaticaly the run method below
    }

    // Using SLEEP method
    public void run() {
        
        double updateInterval = Math.pow(10, 9) / fps; // 0.1666 seconds
        double nextUpdateTime = System.nanoTime() + updateInterval;

        while (gameThread != null) {

            // UPDATE: update information such as character positions
            update();

            // DRAW: draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextUpdateTime - System.nanoTime();
                remainingTime = remainingTime / Math.pow(10, 6); // Thread.sleep accepts only millis

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextUpdateTime = nextUpdateTime + updateInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        if (gameState == playState) {

            player.update();
        }

        if (gameState == pauseState) {


        }
        
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;

        blockM.draw(g2);
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();
        
    }
    
    /**
     * Getter for tileSize.
     * @return tileSize
     */
    public int getTileSize() {
        
        return tileSize;
    }

    /**
     * Getter for maxScreenCol.
     * @return maxScreenCol
     */
    public int getMaxScreenCol() {

        return maxScreenCol;
    }
    
    /**
     * Getter for maxScreenRow.
     * @return maxScreenRow
     */
    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public CollisionChecker getCollisionChecker() {

        return collisionChecker;
    }

    public BlockManager getBlockM() {

        return blockM;
    }
}
