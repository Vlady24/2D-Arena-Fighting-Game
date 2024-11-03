package block;

import java.awt.image.BufferedImage;

/**
 * Class for the blocks of the map.
 */
public class Block {

    private BufferedImage image;
    private boolean collision = false;

    /**
     * Constructor for setting the collision false for each Block object.
     */
    public Block() {
        
    }

    /**
     * Constructor for setting the collision of each Block object.
     * @param collision Boolean for collision.
     */
    public Block(boolean collision) {

        this.collision = collision;
    }
    
    /**
     * Setter for image.
     * @param image The image you want to set. 
     */
    public void setImage(BufferedImage image) {

        this.image = image;
    }

    /**
     * Getter for image.
     * @return image
     */
    public BufferedImage getImage() {

        return image;
    }

    /**
     * Setter for collision.
     * @param collision The collision you want to set.
     */
    public void setCollision(boolean collision) {

        this.collision = collision;
    }
    
    /**
     * Getter for collision.
     * @return collision
     */
    public boolean getCollision() {

        return collision;
    }
}
