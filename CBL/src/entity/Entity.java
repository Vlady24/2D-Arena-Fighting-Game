package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int x;
    public int y;
    public int speed;
    protected int spriteCounter = 0; // trb private sau nu??
    protected int spriteNum = 1;

    // SPRITES
    public BufferedImage up1;
    public BufferedImage up2;
    public BufferedImage down1;
    public BufferedImage down2;
    public BufferedImage left1;
    public BufferedImage left2;
    public BufferedImage right1;
    public BufferedImage right2;

    public String direction;

    private Rectangle collisionArea;

    public boolean collisionOn = false;

    public Rectangle getCollisionArea() {

        return collisionArea;
    }

    public void setCollisionArea(int x, int y, int width, int height) {

        collisionArea = new Rectangle(x, y, width, height);
    }
}