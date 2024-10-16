package main;

import block.BlockManager;
import entity.Entity;
import entity.Monster;
import entity.Player;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    
    // Screen Settings
    private final int originalTileSize = 16; // 16 x 16 tile
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale; // 48 x 48 tile
    private final int maxScreenCol = 24; // 4:3
    private final int maxScreenRow = 18; // ratio
    protected final int screenWidth = tileSize * maxScreenCol; // 1152 pixels
    protected final int screenHeight = tileSize * maxScreenRow; // 864 pixels

    // FPS
    private final int fps = 60; // Framerate per second

    // SYSTEM
    private CollisionChecker collisionChecker = new CollisionChecker(this);
    private BlockManager blockM = new BlockManager(this);
    //private AssetSetter aSetter = new AssetSetter(this);
    private KeyHandler keyHandler = new KeyHandler(this);
    protected UI ui = new UI(this);
    private Thread gameThread;

    // ENTITY
    protected Player player = new Player(this, keyHandler);
    public ArrayList<ArrayList<Monster>> waves;
    public ArrayList<Monster> wave1;
    public ArrayList<Monster> wave2;
    public ArrayList<Monster> wave3;
    public ArrayList<Monster> wave4;
    public ArrayList<Monster> wave5; 
    //= new ArrayList<>() {

//     {
//         // WAVE 1
//         add(new ArrayList<Monster>() {
//             {
//                 add(new Monster(Main.gamePanel, 415, 160));
//                 add(new Monster(Main.gamePanel, 415 + 115, 160));
//                 add(new Monster(Main.gamePanel, 415 + 115 * 2, 160));
//             }
//         });
// 
//         // WAVE 2
//         add(new ArrayList<Monster>() {
//             {
//                 add(new Monster(Main.gamePanel, 415 + 115, 160 + 110));
//             }
//         });
// 
//         // WAVE 3
//         add(new ArrayList<Monster>() {
//             {
//                 add(new Monster(Main.gamePanel, 415, 160 + 110));
//             }
//         });
// 
//         // WAVE 4
//         add(new ArrayList<Monster>() {
//             {
//                 add(new Monster(Main.gamePanel, 415 + 115 * 2, 160 + 110));
//             }
//         });
// 
//         // WAVE 5
//         add(new ArrayList<Monster>() {
//             {
//                 add(new Monster(Main.gamePanel, 415 + 115, 160 + 110 * 2));
//             }
//         });
//
//     }
// };

    // WAVE STATUS
    public int waveNumber = 1;
    private boolean waveCompleted = true;

    // GAME STATE
    protected int gameState;
    protected final int titleState = 0;
    protected final int playState = 1;
    protected final int pauseState = 2;

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

        //aSetter.setMonster();
        gameState = titleState;
        setWaves();
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

            // PLAYER UPDATE
            player.update();

            // MONSTER UPDATE
   //       for (int i = 0; i < wave.size(); i++) {

   //           wave.get(i).update(); // posibil gresit
   //       }
        }

        if (gameState == pauseState) {
            // nothing
        }
        
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;

        //TITLE SCREEN
        if (gameState == titleState) {

            ui.draw(g2);
        } else {
            blockM.draw(g2);
            player.draw(g2);
            for (int i = 0; i < waves.size(); i++) {
                for (int j = 0; j < waves.get(i).size(); j++) {

                    waves.get(i).get(j).draw(g2);
                }
            }
                        
            ui.draw(g2);
            g2.dispose();
        }

        
        
    }

    public void setWaves() {

        waves = new ArrayList<ArrayList<Monster>>();

        // WAVE 1
        wave1 = new ArrayList<Monster>();
        wave1.add(new Monster(this, 415, 160));
        wave1.add(new Monster(this, 415 + 115, 160));
        wave1.add(new Monster(this, 415 + 115 * 2, 160));

        waves.add(wave1);
    
        // WAVE 2
        wave2 = new ArrayList<Monster>();
        wave2.add(new Monster(this, 415 + 115, 160 + 110));

        waves.add(wave2);
    
        // WAVE 3
        wave3 = new ArrayList<Monster>();
        wave3.add(new Monster(this, 415, 160 + 110));

        waves.add(wave3);
    
        // WAVE 4
        wave4 = new ArrayList<Monster>();
        wave4.add(new Monster(this, 415 + 115 * 2, 160 + 110));

        waves.add(wave4);
    
        // WAVE 5
        wave5 = new ArrayList<Monster>();
        wave5.add(new Monster(this, 415 + 115, 160 + 110 * 2));
        
        waves.add(wave5);
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
