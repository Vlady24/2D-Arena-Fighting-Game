package main;

import block.BlockManager;
import entity.Monster;
import entity.Player;
import java.awt.*;
import javax.swing.JPanel;

/**
 * Panel class.
 * 
 * In this class the game loop is implemented, panel settings are initialized and also
 * the sizes of blocks and entities (in pixels).
 * 
 * Class implements Runnable interface in order to use threading.
 */
public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16 x 16 tile
    private final int scale = 3;
    private final int tileSize = originalTileSize * scale; // 48 x 48 tile
    private final int entitySize = tileSize + 32;
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
    private Sound sound = new Sound();
    protected UI ui = new UI(this);
    private Thread gameThread;

    // ENTITY
    protected Player player = new Player(this, keyHandler);
    protected AssetSetter aSetter = new AssetSetter(this); 
    
    // WAVE STATUS
    protected int waveNumber = 1;

    // GAME STATE
    protected int gameState;
    protected final int titleState = 0;
    protected final int playState = 1;
    protected final int pauseState = 2;
    protected final int deathState = 3;
    protected final int winState = 4;
    private int winSoundTimer = 0;

    /**
     * GamePanel class CONSTRUCTOR. 
     * 
     * Sets screen default values, keyboard commands and instantiates the monsters' waves.
     */
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Create the Panel
        this.setDoubleBuffered(true); // Improving game's rendering performance
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.setupGame();
    }

    /**
     * Method used for setting up the game.
     * 
     * Initializes gameState with titleState in order to display main menu when game is opened.
     * 
     * Plays background music.
     */
    public void setupGame() {

        // MENU MUSIC
        playSound(0);

        gameState = titleState;
        aSetter.setWaves();
    }

    /**
     * Method used for starting the gameloop.
     * 
     * Used in Main Class.
     */
    public void startGameThread() {

        gameThread = new Thread(this); // Instantiating the thread
        gameThread.start(); // Starting the thread, calling automatically the run method below
    }

    /**
    * This method implements the game loop that controls the updating and rendering of the game.
    * 
    * The game loop will keep running as long as the gameThread is not null.
    * The game updates and renders at a fixed frame rate, determined by the fps.
    * 
    * The key steps in the method:
    * 
    * 1. Calculate update interval:
    * 
    *    The updateInterval is computed by dividing one second 
    *       (in nanoseconds) by 60 fps.
    * 
    *    This gives the time in nanoseconds that should pass 
    *       between updates to maintain a constant frame rate.
    * 
    * 2. Game loop:
    * 
    *    The while loop continues as long as the game thread is active.
    * 
    *    Inside the loop:
    * 
    *      Update the game state
    * 
    *      Render the game with repaint() method
    * 
    * 3. Sleep to maintain frame rate:
    * 
    *    The time until the next frame update is calculated
    *       as the difference between nextUpdateTime and the current time.
    * 
    *    This difference is converted from nanoseconds to milliseconds,
    *       as Thread.sleep() requires milliseconds.
    * 
    *    If the calculated remaining time is negative (indicating that the game 
    *       is behind schedule), it sets the sleep time to 0 to avoid waiting.
    * 
    * 4. Update the next frame time:
    * 
    *    After sleeping for the remaining time, 
    *       the next update time is recalculated by adding the updateInterval.
    */
    public void run() {
        
        double updateInterval = Math.pow(10, 9) / fps; // 0.1666 seconds
        double nextUpdateTime = System.nanoTime() + updateInterval;

        while (gameThread != null) {

            update();
            repaint();

            // Using SLEEP method
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

    /**
     * Calls the update method for each entity 60 times per second.
     * 
     * Checks for winning condition (all monsters are killed).
     * 
     * Checks for advancing to next wave condition (all monsters in the current wave killed).
     */
    public void update() {

        // CHECKING WIN CONDITION
        if (aSetter.waves.get(4).size() == 0) {

            gameState = winState;

            // ACCESSING THIS IF ONLY ONE TIME
            if (winSoundTimer == 0) {

                playSoundEffect(3);

                winSoundTimer = 1;
            }
        }

        if (gameState == playState) {

            // PLAYER UPDATE
            player.update();

            // WAVE UPDATE
            if (aSetter.waves.get(waveNumber - 1).size() == 0) {

                aSetter.advanceToNextWave();
            }

            // MONSTER UPDATE
            for (int i = 0; i < waveNumber; i++) {
                
                for (int j = 0; j < aSetter.waves.get(i).size(); j++) {

                    if (aSetter.waves.get(i).get(j).getLife() == 0) {

                        Monster monster = aSetter.waves.get(i).get(j);
                        aSetter.waves.get(i).remove(monster);
                        
                    } else {

                        aSetter.waves.get(i).get(j).update(player.getX(), player.getY());
                    }
                }
            }
        }

        if (gameState == pauseState) {
            // nothing
        }

        if (gameState == deathState) {
            // nothing
        }

        if (gameState == winState) {
            // nothing
        }
        
    }

    /**
    * This method handles rendering the game visuals on the screen.
    * It overrides paintComponent() to draw 
    * game elements based on the current game state.
    * 
    * Steps:
    * 
    * 1. Clear previous rendering: 
    *   
    *       Calls super.paintComponent(g) to reset the drawing area.
    * 
    * 2. Draw Title Screen: 
    *       
    *       If the game is in titleState, 
    *           it calls ui.draw(g2) to render the title screen.
    * 
    * 3. Draw Game Elements: If not in the title state, it renders:
    * 
    *       Blocks: 
    * 
    *           Calls blockM.draw(g2) to draw game blocks.
    *       Player: 
    * 
    *           Calls player.draw(g2) to draw the player character.
    *       Monsters: 
    * 
    *           Loops through all monsters in active waves and draws them if they are alive.
    * 
    * 4. Draw UI: 
    * 
    *       Renders the game UI after the other game elements.
    * 
    * 5. Dispose Graphics Object:
    *  
    *      Releases resources used by the Graphics2D object with g2.dispose().
    * 
    * @param g The Graphics object used for drawing.
    */
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

                    // CHECKING IF MONSTER'S LIFE IS ABOVE 0
                    if (aSetter.waves.get(i).get(j).getLife() > 0) {

                        aSetter.waves.get(i).get(j).draw(g2);
                    }
                    
                }
            }
            
            ui.draw(g2);
        }
        
        g2.dispose();
    }

    /**
     * Method for playing the sound.
     * @param index index of the sound.
     */
    public void playSound(int index) {

        sound.setFile(index);
        sound.play();
        sound.loop();
    }

    /**
     * Method for playing the sound effects.
     * 
     * @param index index of the sound.
     */
    public void playSoundEffect(int index) {

        sound.setFile(index);
        sound.play();
    }
   
    /**
     * Getter for tileSize.
     * @return tileSize
     */
    public int getTileSize() {
        
        return tileSize;
    }

    /**
     * Getter for entitySize.
     * @return entitySize
     */
    public int getEntitySize() {
        
        return entitySize;
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

    /**
     * Getter for screenWidth.
     * @return screenWidth
     */
    public int getScreenWidth() {

        return screenWidth;
    }

    /**
     * Getter for screenHeight.
     * @return screenHeight
     */
    public int getScreenHeight() {
        
        return screenHeight;
    }

    /**
     * Getter for collisionChecker.
     * @return collisionChecker
     */
    public CollisionChecker getCollisionChecker() {

        return collisionChecker;
    }

    /**
     * Getter for blockM.
     * @return blockM
     */
    public BlockManager getBlockM() {

        return blockM;
    }

    /**
     * Getter for aSetter.
     * @return aSetter
     */
    public AssetSetter getAssetSetter() {

        return aSetter;
    }

    /**
     * Setter for gameState.
     * @param value the value of the gameState.
     */
    public void setGameState(int value) {

        gameState = value;
    }

    /**
     * Getter for deathState.
     * @return deathState
     */
    public int getDeathState() {

        return deathState;
    }
}
