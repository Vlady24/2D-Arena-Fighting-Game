package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import object.Health;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    // FONTS
    Font arial40;
    Font arial80B; 
    Font algerian;
    Font menuFont;

    // COLORS
    private final Color backgroundColor = new Color(0, 0, 0);
    private final Color titleColor = new Color(153, 204, 255);
    private final Color shadowColor = new Color(0, 76, 153);
    private final Color menuColor = new Color(51, 0, 102);
    private final Color pauseScreen = new Color(0, 0, 0, 127);
    private final Color pauseText = new Color(255, 255, 255);

    private Image titleImage;

    private int commandNum = 0;

    // IMAGES
    BufferedImage backgroundImage;
    BufferedImage emptyHeart;
    BufferedImage halfHeart;
    BufferedImage fullHeart;

    public UI(GamePanel gp) {

        this.gp = gp;

        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
        algerian = new Font("Algerian", Font.BOLD, 100);

        // CREATE HEART OBJECTS
        Health heart = new Health(gp);
        emptyHeart = heart.getEmptyHeart();
        halfHeart = heart.getHalfHeart();
        fullHeart = heart.getFullHeart();
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial80B);
        g2.setColor(Color.BLACK);

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
        }
    }

    public void drawTitleScreen() {
        
        // BACKGROUND COLOR
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // TITLE NAME
        g2.setFont(algerian);
        String text = "Monsters' Arena";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2 - 250;
        
        // TITLE SHADOW
        g2.setColor(shadowColor);
        g2.drawString(text, x + 5, y + 5);

        // TITLE COLOR
        g2.setColor(titleColor);
        g2.drawString(text, x, y);

        // TITLE IMAGE
        try {
            titleImage = ImageIO.read(getClass().getResourceAsStream("/gameicon/titleimage.png"));
            g2.drawImage(titleImage, gp.screenWidth / 2 - 250, y + 50, 500, 500, null);
        } catch (IOException e) {
            
            e.printStackTrace();
        }

        // BACKGROUND IMAGE
        try {
            backgroundImage = ImageIO.read(getClass()
                         .getResourceAsStream("/gameicon/titlepicture.png"));
        } catch (IOException e) {
           e.printStackTrace();
        }
        g2.drawImage(backgroundImage, 0, 0, gp.getScreenWidth(), gp.getScreenHeight(), null);

        // MENU
        text = "NEW GAME";
        g2.setFont(new Font("Algerian", Font.BOLD, 40));
        g2.setColor(menuColor);
        g2.drawString(text, gp.screenWidth / 2 - 100, y + 170);
        if (commandNum == 0) {

            g2.drawString(">", gp.screenWidth / 2 - 135, y + 170);
        }

        text = "QUIT";
        g2.setFont(new Font("Algerian", Font.BOLD, 40));
        g2.setColor(menuColor);
        g2.drawString(text, gp.screenWidth / 2 - 40, y + 240);
        if (commandNum == 1) {

            g2.drawString(">", gp.screenWidth / 2 - 75, y + 240);
        }
    }

    public void drawPlayScreen() {

        int x = 8;

        // DRAW MAX LIFE
        for (int i = 0; i < gp.player.getMaxLife() / 2; i++) {

            g2.drawImage(emptyHeart, x, 42, 64, 64, null);
            x = x + 56;
        }

        // RESET
        x = 8;

        // DRAW CURRENT LIFE
        for (int i = 0; i < gp.player.getLife(); i++) {

            g2.drawImage(halfHeart, x, 42, 64, 64, null);
            i++;
            if (i < gp.player.getLife()) {

                g2.drawImage(fullHeart, x, 42, 64, 64, null);
            }
            x = x + 56;
        }
    }

    public void drawPauseScreen() {

        g2.setColor(pauseScreen);
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());

        String text = "PAUSED";

        // Placing the text right in the center of the screen
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.setColor(pauseText);
        g2.drawString(text, x, y);

        text = "> QUIT";
        g2.setFont(new Font("Algerian", Font.BOLD, 30));
        g2.drawString(text, x + 85, y + 300);
    }

    public int getXforCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }

    public int getCommandNum() {

        return commandNum;
    }

    public void setCommandNum(int value) {
        
        commandNum = value;
    }
}
