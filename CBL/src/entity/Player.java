package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.Movement;

public class Player extends Entity {
    
    private GamePanel gp;
    private Movement movementHandler;

    public Player(GamePanel gp, Movement movementHandler) {

        this.gp = gp;
        this.movementHandler = movementHandler;

        setDefaultValues();
        getPlayerImage();
        setCollisionArea(8, 16, gp.getTileSize() - 16,  gp.getTileSize() - 16);
    }

    public void setDefaultValues() {

        x = 150;
        y = 250;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if (movementHandler.getUpPressed() || movementHandler.getDownPressed() 
            || movementHandler.getLeftPressed() || movementHandler.getRightPressed()) {

            if (movementHandler.getUpPressed()) {

                direction = "up";
            }  
            if (movementHandler.getDownPressed()) {
                
                direction = "down";
            }  
            if (movementHandler.getLeftPressed()) {

                direction = "left";
            }  
            if (movementHandler.getRightPressed()) {

                direction = "right";
            }

            // Checking player collision with block
            collisionOn = false;
            gp.getCollisionChecker().checkTile(this);

            // If collision is false, then the character can move
            if (!collisionOn) {

                switch (direction) {

                    case "up":

                        y = y - speed;
                        break;
                    case "down":
                        
                        y = y + speed;
                        break;
                    case "left":
                        
                        x = x - speed;
                        break;
                    case "right":

                        x = x + speed;
                        break;
                }
            }
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {

                    spriteNum = 2;

                } else if (spriteNum == 2) {

                    spriteNum = 1;
                }
                
                spriteCounter = 0;
            }

        }
        

    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                    
                break;
        
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }

                break;
            
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }

                break;
        }
        
        g2.drawImage(image, x, y, gp.getTileSize(), gp.getTileSize(), null);
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/playerone/1up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/playerone/1up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/playerone/1down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/playerone/1down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/playerone/1left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/playerone/1left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/playerone/1right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/playerone/1right2.png"));

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
