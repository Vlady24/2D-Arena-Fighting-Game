package main;

import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args) {
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Monsters' Arena");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); // Adding the game panel to the window

        window.pack(); // Makes the window to be sized to fit the size of its subcomponents

        window.setLocationRelativeTo(null); // The window will be desplayed in the centre of screen
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
