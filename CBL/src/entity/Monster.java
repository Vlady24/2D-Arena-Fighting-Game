package entity;

import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Monster extends Entity{

    GamePanel gp;

    public Monster() {

    }

    public Monster(GamePanel gp, int x, int y) {

        this.gp = gp;
        this.x = x;
        this.y = y;
        speed = 2;
        direction = "down";
        maxLife = 4;
        life = maxLife;
        setCollisionArea(8, 16, gp.getTileSize() - 16, gp.getTileSize() - 16);
        getMonsterImage();
    }

    public void getMonsterImage() {
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

    public void update() {


    }

    public void draw(Graphics2D g2) {

        g2.drawImage(down1, x, y, gp.getTileSize() + 16, gp.getTileSize() + 16, null);
        
    }

}
