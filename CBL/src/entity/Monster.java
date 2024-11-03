package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 * Monster class that inherits entity. 
 * 
 * Used for monster movement, attack and GUI.
 */
public class Monster extends Entity {

    private GamePanel gp;

    /**
     * Monster class constructor.
     * 
     * Sets default values for each monster.
     */
    public Monster(GamePanel gp, int x, int y) {

        this.gp = gp;
        this.x = x;
        this.y = y;
        speed = 2;
        direction = "down";

        maxLife = 4;
        life = maxLife;
        invincible = false;
        
        setCollisionArea(8, 16, gp.getTileSize() - 16, gp.getTileSize() - 16);
        getMonsterImage();
    }

    /**
     * Method used for retrieving monster's in-game image from file.
     * 
     * Also it generates random monster skins using a random number generator. 
     */
    public void getMonsterImage() {
        try {

            Random random = new Random();
            int i = random.nextInt(1, 3);

            if (i == 1) {

                up1 = ImageIO.read(getClass().getResourceAsStream("/monster1/up1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/monster1/up2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/monster1/down1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/monster1/down2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/monster1/left1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/monster1/left2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/monster1/right1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/monster1/right2.png"));

            } else if (i == 2) {

                up1 = ImageIO.read(getClass().getResourceAsStream("/monster2/up1.png"));
                up2 = ImageIO.read(getClass().getResourceAsStream("/monster2/up2.png"));
                down1 = ImageIO.read(getClass().getResourceAsStream("/monster2/down1.png"));
                down2 = ImageIO.read(getClass().getResourceAsStream("/monster2/down2.png"));
                left1 = ImageIO.read(getClass().getResourceAsStream("/monster2/left1.png"));
                left2 = ImageIO.read(getClass().getResourceAsStream("/monster2/left2.png"));
                right1 = ImageIO.read(getClass().getResourceAsStream("/monster2/right1.png"));
                right2 = ImageIO.read(getClass().getResourceAsStream("/monster2/right2.png"));
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Monster update method.
     * Called in gamePanel update method.
     * 
     * Updates monster movement, damage taken and checks collision with solid blocks
     * and other entities.
     * 
     * @param playerX player's x
     * @param playerY player's y
     */
    public void update(double playerX, double playerY) {

        // CHECKING COLLISION WITH BLOCKS
        collisionBlockOn = false;
        gp.getCollisionChecker().checkTile(this);

        // CHECKING COLLISION WITH OTHER MONSTERS
        collisionMonstersOn = false;
        gp.getCollisionChecker().checkMonsterWithMonster();

        // GETTING THE DISTANCE BETWEEN MONSTER AND CHARAC
        double dx = playerX - this.x;
        double dy = playerY - this.y;
        double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        // NORMALIZE DIRECTION VECTOR
        double directionX = dx / distance;
        double directionY = dy / distance;

        // COPIES OF INITIAL VALUES OF x AND y
        double copyOfx = this.x;
        double copyOfy = this.y;

        // MOVING THE MONSTER TOWARDS THE PLAYER
        this.x = this.x + directionX * speed;
        this.y = this.y + directionY * speed;

        if (this.x >= copyOfx) {

            direction = "right";

            if (collisionBlockOn) {

                this.x = this.x - speed * 1.5;

            }

            if (collisionMonstersOn) {

                this.x = this.x - speed * 0.5;

            }

        } else if (this.x <= copyOfx) {

            direction = "left";

            if (collisionBlockOn) {

                this.x = this.x + speed * 1.5;

            }

            if (collisionMonstersOn) {

                this.x = this.x + speed * 0.5;

            }

        } else if (this.y >= copyOfy) {

            direction = "down";

        } else if (this.y <= copyOfy) {

            direction = "up";

        }

        // WALKING ANIMATION
        spriteCounter++;
        if (spriteCounter > 15) {

            if (spriteNum == 1) {

                spriteNum = 2;

            } else if (spriteNum == 2) {

                spriteNum = 1;
            }

            spriteCounter = 0;
        }

        // MAKES MONSTER INVINCIBLE 45/60 FPS IF IT IS HIT BY PLAYER
        if (invincible) {

            invincibleTime++;

            if (invincibleTime > 45) {

                invincible = false;
                invincibleTime = 0;
            }
        }
    }

    /**
     * Method used for drawing each monster's image in the panel.
     * 
     * @param g2 The Graphics2D object used for drawing.
     */
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

            default:

                break;
        }

        // MONSTER HIT BY PLAYER VISUAL EFFECT
        Composite originalComposite = g2.getComposite(); // copies the original composite
        if (invincible) {

            // MAKING MONSTER LESS OPAQUE IF IT IS HIT BY PLAYER
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g2.drawImage(image, (int) x, (int) y, gp.getEntitySize(), gp.getEntitySize(), null);
            g2.setComposite(originalComposite);

        } else {

            g2.drawImage(image, (int) x, (int) y, gp.getEntitySize(), gp.getEntitySize(), null);
        }

    }

}
