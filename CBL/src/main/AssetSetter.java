
package main;

import entity.Monster;
import java.util.ArrayList;

/**
 * Class used for wave management (spawning monsters and advancing to the next wave).
 */
public class AssetSetter {
    
    GamePanel gp;

    protected ArrayList<ArrayList<Monster>> waves;
    protected ArrayList<Monster> wave1;
    protected ArrayList<Monster> wave2;
    protected ArrayList<Monster> wave3;
    protected ArrayList<Monster> wave4;
    protected ArrayList<Monster> wave5;

    /**
     * Class constructor.
     * 
     * @param gp gamePanel
     */
    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    /**
     * Declares and initializes all the waves.
     * 
     * It uses and ArrayList of ArrayLists of the type Monster.
     * Each element of the ArrayList represent a wave, and each wave contains the monsters
     * spawn coordinates.
     */
    public void setWaves() {

        waves = new ArrayList<ArrayList<Monster>>();

        // WAVE 1
        wave1 = new ArrayList<Monster>();
        
        wave1.add(new Monster(gp, 415, 50));
        wave1.add(new Monster(gp, 415 + 115, 50));
        wave1.add(new Monster(gp, 415 + 115 * 2, 50));

        waves.add(wave1);
    
        // WAVE 2
        wave2 = new ArrayList<Monster>();

        wave2.add(new Monster(gp, 415, 50));
        wave2.add(new Monster(gp, 415 + 115, 50));
        wave2.add(new Monster(gp, 415 + 115 * 2, 50));
        wave2.add(new Monster(gp, 415 + 115, 50 + 110));

        waves.add(wave2);
    
        // WAVE 3
        wave3 = new ArrayList<Monster>();

        wave3.add(new Monster(gp, 415, 50));
        wave3.add(new Monster(gp, 415 + 115, 50));
        wave3.add(new Monster(gp, 415 + 115 * 2, 50));
        wave3.add(new Monster(gp, 415 + 115, 50 + 110));
        wave3.add(new Monster(gp, 415, 50 + 110));

        waves.add(wave3);
    
        // WAVE 4
        wave4 = new ArrayList<Monster>();

        wave4.add(new Monster(gp, 415, 50));
        wave4.add(new Monster(gp, 415 + 115, 50));
        wave4.add(new Monster(gp, 415 + 115 * 2, 50));
        wave4.add(new Monster(gp, 415 + 115, 50 + 110));
        wave4.add(new Monster(gp, 415, 50 + 110));
        wave4.add(new Monster(gp, 415 + 115 * 2, 50 + 110));

        waves.add(wave4);
    
        // WAVE 5
        wave5 = new ArrayList<Monster>();

        wave5.add(new Monster(gp, 415, 50));
        wave5.add(new Monster(gp, 415 + 115, 50));
        wave5.add(new Monster(gp, 415 + 115 * 2, 50));
        wave5.add(new Monster(gp, 415 + 115, 50 + 110));
        wave5.add(new Monster(gp, 415, 50 + 110));
        wave5.add(new Monster(gp, 415 + 115 * 2, 50 + 110));
        wave5.add(new Monster(gp, 415 + 115, 50 + 110 * 2));
        
        waves.add(wave5);
    }

    /**
     * Method used for retrieving the instances of monsters in the current wave.
     * 
     * @return monsters (an ArrayList of the current wave monsters)
     */
    public ArrayList<Monster> getCurrentWaveMonsters() {

        ArrayList<Monster> monsters = new ArrayList<Monster>();

        for (int i = 0; i < gp.waveNumber; i++) {
            for (int j = 0; j < waves.get(i).size(); j++) {

                monsters.add(waves.get(i).get(j));
            }
            
        }

        return monsters;
    }

    /**
     * Method used for advancing to the next wave when all monsters die.
     * 
     * Increases the waveNumber and also gives back the player 1.5 hearts if missing after 
     * completing a wave. 
     */
    public void advanceToNextWave() {

        if (gp.waveNumber < waves.size()) {

            gp.waveNumber++;
            
            // RESTORING PLAYER'S LIFE BASED ON CURRENT LIFE
            int currentLife = gp.player.getLife();
            
            if (gp.player.getLife() == gp.player.getMaxLife() - 1) {

                gp.player.setLife(currentLife + 1);

            } else if (gp.player.getLife() == gp.player.getMaxLife() - 2) {

                gp.player.setLife(currentLife + 2);
                
            } else if (gp.player.getLife() <= gp.player.getMaxLife() - 3) {

                gp.player.setLife(currentLife + 3);
            } 
        }
    }

}
