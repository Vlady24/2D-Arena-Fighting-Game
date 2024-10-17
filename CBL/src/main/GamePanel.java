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
    private final int screenWidth = tileSize * maxScreenCol; // 1152 pixels
    private final int screenHeight = tileSize * maxScreenRow; // 864 pixels

    // FPS
    private final int fps = 60; // Framerate per second

    // SYSTEM
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private BlockManager blockM = new BlockManager(this);
    private KeyHandler keyHandler = new KeyHandler(this);
    protected UI ui = new UI(this);
    private Thread gameThread;

    // ENTITY
    protected Player player = new Player(this, keyHandler);
    protected AssetSetter aSetter = new AssetSetter(this); 
    
    // WAVE STATUS
    protected int waveNumber = 1;
    private boolean waveCompleted = true; // trebuie folosit

    // GAME STATE
    public int gameState;
    protected final int titleState = 0;
    protected final int playState = 1;
    protected final int pauseState = 2;
    public final int deathState = 3;

    /**
     * GamePanel class CONSTRUCTOR. 
     * 
     */
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Create the Panel
        this.setDoubleBuffered(true); // Improving game's rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setupGame();
    }

    public void setupGame() {

        gameState = titleState;
        aSetter.setWaves();
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

            update();
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

            // PLAYER UPDATE
            player.update();

            // MONSTER UPDATE
            for (int i = 0; i < waveNumber; i++) {
                for (int j = 0; j < aSetter.waves.get(i).size(); j++) {

                    aSetter.waves.get(i).get(j).update();
                }
            }
        }

        if (gameState == pauseState) {
            // nothing
        }

        if (gameState == deathState) {
            // nothing
        }
        
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;

        // TITLE SCREEN
        if (gameState == titleState) {

            ui.draw(g2);
        } else {
            blockM.draw(g2);
            player.draw(g2);

            // DRAWING THE MONSTERS
            for (int i = 0; i < waveNumber; i++) {
                for (int j = 0; j < aSetter.waves.get(i).size(); j++) {

                    aSetter.waves.get(i).get(j).draw(g2);
                }
            }
                        
            ui.draw(g2);
        }
        
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

    public int getScreenWidth() {

        return screenWidth;
    }

    public int getScreenHeight() {
        
        return screenHeight;
    }

    public CollisionChecker getCollisionChecker() {

        return collisionChecker;
    }

    public BlockManager getBlockM() {

        return blockM;
    }
}
