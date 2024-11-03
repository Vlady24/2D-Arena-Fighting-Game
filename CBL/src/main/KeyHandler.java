package main;

import java.awt.event.*;

/**
 * Class for implementing the KeyListener.
 * 
 * Every keyboard interaction is defined here.
 */
public class KeyHandler implements KeyListener {

    private GamePanel gp;

    // KEYS
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean attackPressed;

    /**
     * KeyHandler class CONSTRUCTOR.
     * @param gp GamePanel Object
     * 
     */
    public KeyHandler(GamePanel gp) {

        this.gp = gp;
    }

    // Since the game doesn't involve typing anyhing we don't need to implement
    // this method
    public void keyTyped(KeyEvent e) {} 

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {

            if (code == KeyEvent.VK_DOWN) {

                gp.ui.setCommandNum(1);
            }

            if (code == KeyEvent.VK_UP) {

                gp.ui.setCommandNum(0);
            }

            if (code == KeyEvent.VK_ENTER) {

                if (gp.ui.getCommandNum() == 0) {

                    gp.gameState = gp.playState;
                }

                if (gp.ui.getCommandNum() == 1) {

                    System.exit(0);
                }
            }
        }

        // PLAY STATE
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        
        if (code == KeyEvent.VK_SPACE) {
            attackPressed = true;
        }

        // PAUSE STATE
        if (code == KeyEvent.VK_ESCAPE) {
            
            if (gp.gameState == gp.playState) {

                gp.gameState = gp.pauseState;

            } else if (gp.gameState == gp.pauseState) {

                gp.gameState = gp.playState;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.gameState == gp.pauseState) {
                System.exit(0);
            }
        }

        // DEATH STATE
        if (gp.gameState == gp.deathState) {

            if (code == KeyEvent.VK_ENTER) {
                
                System.exit(0);
            }
        }

        // WIN STATE
        if (gp.gameState == gp.winState) {

            if (code == KeyEvent.VK_ENTER) {
                
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_SPACE) {
            
            attackPressed = false;
        }
    }
    
    /**
     * Getter for upPressed.
     * @return upPressed
     */
    public boolean getUpPressed() {

        return upPressed;
    }

    /**
     * Getter for downPressed.
     * @return downPressed
     */
    public boolean getDownPressed() {

        return downPressed;
    }

    /**
     * Getter for leftPressed.
     * @return leftPressed
     */
    public boolean getLeftPressed() {

        return leftPressed;
    }

    /**
     * Getter for rightPressed.
     * @return rightPressed
     */
    public boolean getRightPressed() {

        return rightPressed;
    }

    /**
     * Getter for attackPressed.
     * @return attackPressed
     */
    public boolean getAttackPressed() {

        return attackPressed;
    }
}
