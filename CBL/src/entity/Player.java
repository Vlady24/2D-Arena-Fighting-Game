package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    
    private GamePanel gp;
    private KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler keyHandler) {

        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
        setCollisionArea(8, 16, gp.getTileSize() - 16,  gp.getTileSize() - 16);
    }

    public void setDefaultValues() {

        // STARTING COORDINATES
        x = 520;
        y = 700;

        // PLAYER SPEED
        speed = 4;

        direction = "down";

        // PLAYER LIFE
        setMaxLife(6);
        setLife(getMaxLife());
    }

    public void update() {

        if (keyHandler.getUpPressed() || keyHandler.getDownPressed() 
            || keyHandler.getLeftPressed() || keyHandler.getRightPressed()) {

            if (keyHandler.getUpPressed()) {

                direction = "up";
            }  
            if (keyHandler.getDownPressed()) {
                
                direction = "down";
            }  
            if (keyHandler.getLeftPressed()) {

                direction = "left";
            }  
            if (keyHandler.getRightPressed()) {

                direction = "right";
            }

            // Checking player collision with block
            collisionBlockOn = false;
            gp.getCollisionChecker().checkTile(this);

            // Checking player collision with monsters
            collisionMonstersOn = false;
            gp.getCollisionChecker().checkMonsters(this);
            

            // If collision is false, then the character can move
            if (!collisionBlockOn) {

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
                //System.out.println(x + " " + y);
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
        
        // LOSING LIFE ON MONSTER HIT  + RED SCREEN???????
        if (collisionMonstersOn && !invincible) {

            life = life - 1;
            invincible = true;
        }

        if (invincible) {
            
            invincibleTime++;

            if (invincibleTime > 20) {
                
                invincible = false;
                invincibleTime = 0;
            }
        }
        if (life == 0) {
            
            gp.gameState = gp.deathState;
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
        
        g2.drawImage(image, x, y, gp.getTileSize() + 16, gp.getTileSize() + 16, null);
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/playerone/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/playerone/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/playerone/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/playerone/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/playerone/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/playerone/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/playerone/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/playerone/right2.png"));

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
