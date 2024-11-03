package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * Entity class used to manage player and monster movement + interaction.
 */
public class Entity {
    
    // ENITTY MOVEMENT
    protected double x;
    protected double y;
    protected int speed;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;
    protected String direction;

    // ENTITY MOVEMENT SPRITES
    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;

    // ENTITY ATTACK SPRITES
    protected BufferedImage attackUp;
    protected BufferedImage attackDown;
    protected BufferedImage attackLeft;
    protected BufferedImage attackRight;
    protected int drawTimer = 45;

    // ENTITY HITBOX
    protected Rectangle collisionArea; // Player's hitbox
    protected Rectangle attackArea; // Sword's hitbox
    protected boolean collisionBlockOn = false;
    protected boolean collisionMonstersOn = false;

    // ENTITY STATUS
    protected int maxLife;
    protected int life;
    protected boolean invincible = false;
    protected int invincibleTime = 0;

    /**
     * Getter for attackArea.
     * @return attackArea
     */
    public Rectangle getAttackArea() {

        return attackArea;
    }

    /**
     * Setter for attackArea.
     */
    public void setAttackArea(int x, int y, int width, int height) {

        attackArea = new Rectangle(x, y, width, height);
    }

    /**
     * Getter for collisionArea.
     * @return collisionArea
     */
    public Rectangle getCollisionArea() {

        return collisionArea;
    }

    /**
     * Setter for collisionArea.
     * 
     * @param x player's x
     * @param y player's y
     * @param width hitbox width
     * @param height hitbox height
     * 
     */
    public void setCollisionArea(int x, int y, int width, int height) {

        collisionArea = new Rectangle(x, y, width, height);
    }

    /**
     * Getter for maxLife.
     * @return maxLife
     */
    public int getMaxLife() {

        return maxLife;
    }

    /**
     * Setter for maxLife.
     */
    public void setMaxLife(int maxLife) {

        this.maxLife = maxLife;
    }

    /**
     * Getter for life.
     * @return life
     */
    public int getLife() {
        
        return life;
    }

    /**
     * Setter for life.
     */
    public void setLife(int life) {
        
        this.life = life;
    }

    /**
     * Getter for invincible.
     * @return invincible
     */
    public boolean getInvincible() {

        return invincible;
    }

    /**
     * Getter for x.
     * @return x
     */
    public double getX() {

        return x;
    }

    /**
     * Getter for y.
     * @return y
     */
    public double getY() {

        return y;
    }

    /**
     * Getter for direction.
     * @return direction
     */
    public String getDirection() {

        return direction;
    }

    /**
     * Getter for speed.
     * @return speed
     */
    public int getSpeed() {

        return speed;
    }

    /**
     * Setter for x.
     */
    public void setX(double value) {

        x = value;
    }
    
    /**
     * Setter for y.
     */
    public void setY(double value) {

        y = value;
    }

    /**
     * Setter for collisionBlockOn.
     */
    public void setCollisionBlockOn(boolean value) {

        collisionBlockOn = value;
    }

    /**
     * Setter for collisionMonstersOn.
     */
    public void setCollisionMonstersOn(boolean value) {

        collisionMonstersOn = value;
    }
}