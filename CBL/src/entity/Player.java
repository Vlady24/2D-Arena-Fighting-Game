package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

/**
 * Class for the main character of the game.
 */
public class Player extends Entity {
    
    private GamePanel gp;
    private KeyHandler keyHandler;
    private boolean attacking = false;
    private int attackFrameCounter = 0;
    private static final int ATTACK_DURATION = 25;

    /**
     * Constructor for player class.
     * Setting the default values, the iamges for the character, 
     * the collision area and the attack hitbox.
     * @param gp GamePanel
     * @param keyHandler Key listener
     */
    public Player(GamePanel gp, KeyHandler keyHandler) {

        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
        setCollisionArea(8, 16, gp.getTileSize() - 16,  gp.getTileSize() - 16);
        setAttackArea(0, 0, (gp.getTileSize() + 32) / 2, (gp.getTileSize() + 32) / 2);
    }

    /**
     * Setting the initial values of the player position, speed and health.
     */
    public void setDefaultValues() {

        // STARTING COORDINATES
        x = 520;
        y = 700;

        // PLAYER'S SPEED
        speed = 4;

        direction = "down";

        // PLAYER LIFE
        setMaxLife(6);
        setLife(getMaxLife());
    }

    /**
     * Update method of the character.
     * Creating the movement of the player.
     */
    public void update() {

        if (attacking) {

            attacking();
        }

        if (!attacking && (keyHandler.getUpPressed() || keyHandler.getDownPressed() 
            || keyHandler.getLeftPressed() || keyHandler.getRightPressed()
            || keyHandler.getAttackPressed())) {   

            // Setting the player position. 
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
            gp.getCollisionChecker().checkMonsterWithPlayer(this);
            

            // If collision is false, then the character can move
            if (!collisionBlockOn && !attacking) {
                
                if (direction == "up") {

                    y = y - speed;
                }

                if (direction == "down") {

                    y = y + speed;
                }

                if (direction == "left") {

                    x = x - speed;
                }

                if (direction == "right") {

                    x = x + speed;
                }
            
            }

            // Creating the movement animation of the character.
            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNum == 1) {

                    spriteNum = 2;

                } else if (spriteNum == 2) {

                    spriteNum = 1;
                }
                
                spriteCounter = 0;
            }

            // Attack if the space is pressed.
            if (keyHandler.getAttackPressed()) {
            
                attacking = true;
                attackFrameCounter = 0;
            }

        }
        
        // LOSING LIFE ON MONSTER HIT AND SOUND EFFECT
        if (collisionMonstersOn && !invincible) {

            gp.playSoundEffect(1);
            
            life--;
            invincible = true;
        }
         
        // Creating the invincible period of the player if hit by a monster.
        if (invincible) {
            
            invincibleTime++;

            if (invincibleTime > 120) {
                
                invincible = false;
                invincibleTime = 0;
            }
        }

        // Ending the game if the player's life is 0.
        if (life == 0) {
            
            gp.setGameState(gp.getDeathState());
        }
    }

    /**
     * Drawing the character on the screen if attacking or moving.
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        boolean attackToggleUp = false;
        boolean attackToggleDown = false;
        boolean attackToggleLeft = false;
        boolean attackToggleRight = false;

        switch (direction) {

            case "up":

                if (!attacking) {

                    if (spriteNum == 1) {
                        image = up1;
                    } else if (spriteNum == 2) {
                        image = up2;
                    }
                } 
                if (attacking) {
                    image = attackUp;
                    attackToggleUp = true;
                }
                 
                break;
        
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                } 
                if (attacking) {
                    image = attackDown;
                    attackToggleDown = true;;
                }
                
                break;
            
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                } 
                if (attacking) {
                    image = attackLeft;
                    attackToggleLeft = true;;
                }
                
                break;

            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                } 
                if (attacking) {
                    image = attackRight;
                    attackToggleRight = true;
                }
                
                break;
            default:
                break;
        }
        
        // SCALING THE IMAGE BASED ON THE DIRECTION OF THE ATTACK
        if (attackToggleUp) {
            
            g2.drawImage(image, (int) x, (int) y - gp.getEntitySize() / 2, 
                        gp.getEntitySize(), gp.getEntitySize() + gp.getEntitySize() / 2, null);
                
        } else if (attackToggleDown) {
            
            g2.drawImage(image, (int) x, (int) y, gp.getEntitySize(), 
                        gp.getEntitySize() + gp.getEntitySize() / 2, null);

        } else if (attackToggleLeft) {

            g2.drawImage(image, (int) x - gp.getEntitySize() / 2, (int) y, 
                        gp.getEntitySize() + gp.getEntitySize() / 2, gp.getEntitySize(), null);

        } else if (attackToggleRight) {

            g2.drawImage(image, (int) x, (int) y, 
                        gp.getEntitySize() + gp.getEntitySize() / 2, gp.getEntitySize(), null);
            
        } else {

            g2.drawImage(image, (int) x, (int) y, 
                        gp.getEntitySize(), gp.getEntitySize(), null);

        }
    }

    /**
     * Method for attacking.
     */
    public void attacking() {

        attackFrameCounter++;

        // SAVE THE CURRENT x, y, collisionArea
        double currentX = x;
        double currentY = y;
        int collisionAreaWidth = collisionArea.width;
        int collisionAreaHeight = collisionArea.height;

        // ADJUST PLAYER'S x and y for the attackArea
        switch (direction) {

            case "up":
                    
                y = y - attackArea.height;
                break;
            
            case "down":

                y = y + attackArea.height;
                break;

            case "left":
                    
                x = x - attackArea.height;
                break;

            case "right":
                    
                x = x + attackArea.height;
                break;
            default:
                break;
        }

        // CollisonArea becomes attackArea
        collisionArea.width = attackArea.width;
        collisionArea.height = attackArea.height;

        int monsterIndex = gp.getCollisionChecker().checkMonsterWithSword(this);

        // Damage monster if you hit any.
        if (monsterIndex != -1) {

            damageMonster(monsterIndex);
        } 

        // Timer for attack animation.
        if (attackFrameCounter >= ATTACK_DURATION) {

            attacking = false;
            attackFrameCounter = 0;
        } 

        // Restoring the original values.
        x = currentX;
        y = currentY;
        collisionArea.width = collisionAreaWidth;
        collisionArea.height = collisionAreaHeight;
    }

    /**
     * Method for damaging monsters.
     * @param index index of the monster hit.
     */
    private void damageMonster(int index) {

        ArrayList<Monster> monsters = gp.getAssetSetter().getCurrentWaveMonsters();
        
        // CHECK IF MONSTER IS INVINCIBLE AND SOUND EFFECT
        if (!monsters.get(index).invincible) {
            
            gp.playSoundEffect(2);

            monsters.get(index).life--;
            monsters.get(index).invincible = true;
        }
    }

    /**
     * Method for setting the player images for each movement or attacking animation.
     */
    public void getPlayerImage() {

        try {

            // MOVEMENT IMAGES
            up1 = ImageIO.read(getClass().getResourceAsStream("/playerone/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/playerone/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/playerone/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/playerone/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/playerone/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/playerone/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/playerone/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/playerone/right2.png"));

            // ATTACK IMAGES
            attackUp = ImageIO.read(getClass().getResourceAsStream("/playerone/attackup.png"));
            attackDown = ImageIO.read(getClass().getResourceAsStream("/playerone/attackdown.png"));
            attackLeft = ImageIO.read(getClass().getResourceAsStream("/playerone/attackleft.png"));
            attackRight = ImageIO.read(getClass().getResourceAsStream(
                                                                    "/playerone/attackright.png"));
            
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
