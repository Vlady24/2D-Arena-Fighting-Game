package main;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial40;
    Font arial80B; 
    boolean messageOn = false;
    String message = "";
    int messageCounter = 0;
    boolean gameFinished = false;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {

        this.gp = gp;

        arial40 = new Font("Arial", Font.PLAIN, 40);
        arial80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial80B);
        g2.setColor(Color.WHITE);

        switch (gp.gameState) {

            case 1:

                drawPlayScreen();
                break;

            case 2:

                drawPauseScreen();
                break;
        }
    }

    public void drawPlayScreen() {
        
    }

    public void drawPauseScreen() {

        String text = "PAUSED";

        // Placing the text right in the center of the screen
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;

        return x;
    }
}
