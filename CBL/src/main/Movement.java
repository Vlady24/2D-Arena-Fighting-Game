package main;

import java.awt.event.*;

public class Movement implements KeyListener {

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    // Since the game doesn't involve typing anyhing we don't need to implement
    // this method
    public void keyTyped(KeyEvent e) {} 

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

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
}