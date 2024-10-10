package block;

import java.awt.image.BufferedImage;

public class Block {

    private BufferedImage image;
    private boolean collision = false;

    public Block() {
        
    }

    public Block(boolean collision) {

        this.collision = collision;
    }
    
    public void setImage(BufferedImage image) {

        this.image = image;
    }

    public BufferedImage getImage() {

        return image;
    }

    public void setCollision(boolean collision) {

        this.collision = collision;
    }
    
    public boolean getCollision() {

        return collision;
    }
}
