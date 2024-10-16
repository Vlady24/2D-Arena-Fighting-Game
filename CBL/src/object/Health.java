package object;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 * Class for Health.
 */
public class Health {

    GamePanel gp;

    // HEART IMAGES
    BufferedImage emptyHeart;
    BufferedImage halfHeart;
    BufferedImage fullHeart;

    /**
     * Constructor for Health class.
     * @param gp GamePanel object
     */
    public Health(GamePanel gp) {
        
        this.gp = gp;

        try {
            
            emptyHeart = ImageIO.read(getClass().getResourceAsStream("/hearts/emptyheart.png"));
            halfHeart = ImageIO.read(getClass().getResourceAsStream("/hearts/halfheart.png"));
            fullHeart = ImageIO.read(getClass().getResourceAsStream("/hearts/fullheart.png"));

        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
    
    /**
     * Getter for emptyHeart.
     * @return emptyHeart
     */
    public BufferedImage getEmptyHeart() {

        return emptyHeart;
    }

    /**
     * Getter for halfHeart.
     * @return halfHeart
     */
    public BufferedImage getHalfHeart() {

        return halfHeart;
    }

    /**
     * Getter for fullHeart.
     * @return fullHeart
     */
    public BufferedImage getFullHeart() {

        return fullHeart;
    }
}
