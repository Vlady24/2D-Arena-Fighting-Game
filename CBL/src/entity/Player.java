package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.Movement;

public class Player extends Entity {
    
    GamePanel gp;
    Movement movementHandler;

    public Player(GamePanel gp, Movement movementHandler) {

        this.gp = gp;
        this.movementHandler = movementHandler;

        setDefaultValues();
        getPlayerImage();

        collisionArea = new Rectangle(8, 16, gp.getTileSize() - 16, gp.getTileSize() - 16);
    }

    public void setDefaultValues() {

        x = 350;
        y = 400;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if (movementHandler.getUpPressed() || movementHandler.getDownPressed() 
            || movementHandler.getLeftPressed() || movementHandler.getRightPressed()) {

            if (movementHandler.getUpPressed()) {
                direction = "up";
                y = y - speed;
            }  
            if (movementHandler.getDownPressed()) {
                direction = "down";
                y = y + speed;
            }  
            if (movementHandler.getLeftPressed()) {
                direction = "left";
                x = x - speed;
            }  
            if (movementHandler.getRightPressed()) {
                direction = "right";
                x = x + speed;
            }

            collisionOn = false;
            gp.collisionChecker.checkTile(this);

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
