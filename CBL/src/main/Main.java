/**
 * @author  RAUL-ANDREI MOCIORNITA
 * @id      2123649
 * @author  VLADIMIR-ANDREI ZSEHRANSZKY
 * @id      2083906
 */

package main;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The Main class.
 */
public class Main {
    
    //public static GamePanel gamePanel;

    public Main() {

        GamePanel gamePanel = new GamePanel();
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Monsters' Arena");

        window.add(gamePanel); // Adding the game panel to the window

        window.pack(); // Makes the window to be sized to fit the size of its subcomponents

        window.setLocationRelativeTo(null); // The window will be desplayed in the centre of screen
        window.setVisible(true);

        try {
            Image icon = ImageIO.read(Main.class.getResourceAsStream("/gameicon/gameicon.png"));
            window.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
            

        gamePanel.startGameThread();
    }
    public static void main(String[] args) {
        
        new Main();
    }
}
