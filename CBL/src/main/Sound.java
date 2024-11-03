package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class for the sounds of the game.
 */
public class Sound {

    private Clip clip;
    private URL[] soundURL;

    /**
     * Constructor for the sound class.
     * Setting each sound.
     */
    public Sound() {
        
        soundURL = new URL[4];

        soundURL[0] = getClass().getResource("/sounds/battlemusic.wav"); // Background music
        soundURL[1] = getClass().getResource("/sounds/manhurt.wav"); // Player hit sound effect
        soundURL[2] = getClass().getResource("/sounds/monsterhit.wav"); // Monster hit sound effect
        soundURL[3] = getClass().getResource("/sounds/winsound.wav"); // Win sound effect
    }

    /**
     * Method for playing the sounds.
     */
    public void play() {

        clip.start();
    }

    /**
     * Method for repeating the same sound.
     */
    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Method for setting the file of the sound.
     * @param index index of the desired sound. 
     */
    public void setFile(int index) {

        try {

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[index]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }


}
