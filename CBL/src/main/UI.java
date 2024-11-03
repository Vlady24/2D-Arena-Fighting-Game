package main;

import healthsys.Health;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class for the User Interface of the game.
 * 
 * Every object (heart, wave number, visual effects, invincibility etc.) is defined here.
 */
public class UI {

    private GamePanel gp;
    private Graphics2D g2;

    // FONTS
    private final Font arial80B; 
    private final Font algerian;

    // COLORS
    private final Color titleColor;
    private final Color shadowColor;
    private final Color menuColor;
    private final Color pauseScreen;
    private final Color pauseText;
    private final Color deathScreen;
    private final Color hitColor;
    private final Color winScreen;

    // MENU SELECTOR
    private int commandNum = 0;

    // IMAGES
    private BufferedImage backgroundImage;
    private BufferedImage emptyHeart;
    private BufferedImage halfHeart;
    private BufferedImage fullHeart;

    /**
     * Constructor for the User Interface.
     * Setting fonts, colors and the health system.
     * @param gp GamePanel
     */
    public UI(GamePanel gp) {

        this.gp = gp;

        // FONTS
        arial80B = new Font("Arial", Font.BOLD, 80);
        algerian = new Font("Algerian", Font.BOLD, 100);

        // COLOURS
        titleColor = new Color(153, 204, 255);
        shadowColor = new Color(0, 76, 153);
        menuColor = new Color(51, 0, 102);
        pauseScreen = new Color(0, 0, 0, 130);
        pauseText = new Color(255, 255, 255);
        deathScreen = new Color(255, 0, 0, 150);
        hitColor = new Color(255, 0, 0, 20);
        winScreen = new Color(44, 233, 63, 150);

        // CREATE HEART OBJECTS
        Health heart = new Health(gp);
        emptyHeart = heart.getEmptyHeart();
        halfHeart = heart.getHalfHeart();
        fullHeart = heart.getFullHeart();
    }

    /**
     * Method for drawing each screen.
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        switch (gp.gameState) {

            // TITLE STATE
            case 0:

                drawTitleScreen();
                break;

            // PLAY STATE
            case 1:

                drawPlayScreen();
                break;

            // PAUSE STATE
            case 2:

                drawPauseScreen();
                break;
            
            // DEATH STATE
            case 3:
                
                drawDeathScreen();
                break;
                
            // WIN STATE
            case 4:

                drawWinScreen();
                break;
            default:

                break;
        }
    }

    /**
     * Method for drawing the title screen of the game.
     */
    public void drawTitleScreen() {
        
        // TITLE NAME
        g2.setFont(algerian);
        String text = "Monsters' Arena";
        int x = getXforCenteredText(text);
        int y = gp.getScreenHeight() / 2 - 320;
        
        // TITLE SHADOW
        g2.setColor(shadowColor);
        g2.drawString(text, x + 5, y + 5);

        // TITLE COLOR
        g2.setColor(titleColor);
        g2.drawString(text, x, y);

        // BACKGROUND IMAGE
        try {
            backgroundImage = ImageIO.read(getClass()
                         .getResourceAsStream("/menu/menuphoto.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(backgroundImage, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

        // MENU
        text = "NEW GAME";
        g2.setFont(new Font("Helvetica", Font.BOLD, 40));
        g2.setColor(menuColor);
        g2.drawString(text, gp.getScreenWidth() / 2 - 100, y + 170);
        if (commandNum == 0) {

            g2.drawString(">", gp.getScreenWidth() / 2 - 135, y + 170);
        }

        // QUIT BUTTON
        text = "QUIT";
        g2.setFont(new Font("Helvetica", Font.BOLD, 40));
        g2.setColor(menuColor);
        g2.drawString(text, gp.getScreenWidth() / 2 - 40, y + 240);
        if (commandNum == 1) {

            g2.drawString(">", gp.getScreenWidth() / 2 - 75, y + 240);
        }
    }

    /**
     * Method for drawing the play screen of the game.
     */
    public void drawPlayScreen() {

        int x = 100;
        int y = -10;

        // DRAW MAX LIFE
        for (int i = 0; i < gp.player.getMaxLife() / 2; i++) {

            g2.drawImage(emptyHeart, x, y, 64, 64, null);
            x = x + 56;
        }

        // RED SCREEN WHEN PLAYER IS HIT BY MONSTER
        if (gp.player.getInvincible()) {

            g2.setColor(hitColor);
            g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        }

        // RESET
        x = 100;

        // DRAW CURRENT LIFE
        for (int i = 0; i < gp.player.getLife(); i++) {

            g2.drawImage(halfHeart, x, y, 64, 64, null);
            i++;
            if (i < gp.player.getLife()) {

                g2.drawImage(fullHeart, x, y, 64, 64, null);
            }
            x = x + 56;
        }

        // DRAW WAVE NUMBER
        String text = "WAVE " + gp.waveNumber + "/5";
        g2.setFont(new Font("Algerian", Font.BOLD, 45));
        g2.setColor(Color.BLACK);
        
        // RESET
        x = getXforCenteredText(text);
        y = 855;

        g2.drawString(text, x, y);
    
        // ANNOUNCING INVINCIBILITY
        if (gp.player.getInvincible()) {

            String textInvincible = "INVINCIBLE ON!";

            g2.setFont(new Font("Algerian", Font.BOLD, 20));
            g2.setColor(Color.BLACK);

            x = 885;
            y = 30;

            g2.drawString(textInvincible, x, y);
        }
    }

    /**
     * Method for drawing the pause screen of the game.
     */
    public void drawPauseScreen() {

        // Drawing a semi-transparent black rectangle on the whole screen.
        g2.setColor(pauseScreen);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Drawing the "PAUSED" text
        String text = "PAUSED";
        g2.setFont(arial80B);
        g2.setColor(pauseText);

        // Placing the text right in the center of the screen
        int x = getXforCenteredText(text);
        int y = gp.getScreenHeight() / 2;
 
        g2.drawString(text, x, y);

        // Drawing the "> QUIT" text
        text = "> QUIT";
        g2.setFont(new Font("Algerian", Font.BOLD, 30));

        x = getXforCenteredText(text);
        
        g2.drawString(text, x, y + 300);
    }

    /**
     * Method for drawing the death screen of the game.
     */
    public void drawDeathScreen() {

        // Drawing a semi-transparent red rectangle on the whole screen.
        g2.setColor(deathScreen);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Drawing the "YOU DIED" text
        String text = "YOU DIED!";
        g2.setFont(arial80B);
        g2.setColor(pauseText);

        // Placing the text right in the center of the screen
        int x = getXforCenteredText(text);
        int y = gp.getScreenHeight() / 2;

        g2.drawString(text, x, y);

        // Drawing the "> QUIT" text
        text = "> QUIT";
        g2.setFont(new Font("Algerian", Font.BOLD, 30));
        x = getXforCenteredText(text);
        
        g2.drawString(text, x, y + 300);

        // Drawing how many waves the player survived
        text = "WAVES COMPLETED: " + (gp.waveNumber - 1);
        g2.setFont(new Font("Alegrian", Font.BOLD, 45));
        x = getXforCenteredText(text);
        y = 100;

        g2.drawString(text, x, y);
    }

    /**
     * Drawing the win screen of the game.
     */
    public void drawWinScreen() {

        // Drawing a semi-transparent green rectangle on the whole screen.
        g2.setColor(winScreen);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        // Drawing the "YOU WON!" text
        String text = "YOU WON!";
        g2.setFont(arial80B);
        g2.setColor(pauseText);

        // Placing the text right in the center of the screen
        int x = getXforCenteredText(text);
        int y = gp.getScreenHeight() / 2;

        g2.drawString(text, x, y);

        // Drawing the "> QUIT" text
        text = "> QUIT";
        g2.setFont(new Font("Algerian", Font.BOLD, 30));
        x = getXforCenteredText(text);
        
        g2.drawString(text, x, y + 300);
    }

    /**
     * Method for getting the center of the screen for a String depending on the font.
     * @param text String text
     * @return x coordinate
     */
    public int getXforCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.getScreenWidth() / 2 - length / 2;

        return x;
    }

    /**
     * Getter for commandNum.
     * @return commandNum
     */
    public int getCommandNum() {

        return commandNum;
    }

    /**
     * Setter for comandNum.
     * @param value the value you want to set.
     */
    public void setCommandNum(int value) {
        
        commandNum = value;
    }
}
