package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int x;
    public int y;
    public int speed;
    protected int spriteCounter = 0;
    protected int spriteNum = 1;

    // SPRITES
    protected BufferedImage up1;
    protected BufferedImage up2;
    protected BufferedImage down1;
    protected BufferedImage down2;
    protected BufferedImage left1;
    protected BufferedImage left2;
    protected BufferedImage right1;
    protected BufferedImage right2;

    public String direction;

    private Rectangle collisionArea;

    public boolean collisionOn = false;

    // CHARACTER STATUS
    protected int maxLife;
    protected int life;

    public Rectangle getCollisionArea() {

        return collisionArea;
    }

    public void setCollisionArea(int x, int y, int width, int height) {

        collisionArea = new Rectangle(x, y, width, height);
    }

    public int getMaxLife() {

        return maxLife;
    }

    public void setMaxLife(int maxLife) {

        this.maxLife = maxLife;
    }

    public int getLife() {
        
        return life;
    }

    public void setLife(int life) {
        
        this.life = life;
    }
}