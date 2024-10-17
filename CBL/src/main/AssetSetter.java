
package main;

import entity.Monster;
import java.util.ArrayList;
import java.util.List;

public class AssetSetter {
    
    GamePanel gp;

    protected ArrayList<ArrayList<Monster>> waves;
    protected ArrayList<Monster> wave1;
    protected ArrayList<Monster> wave2;
    protected ArrayList<Monster> wave3;
    protected ArrayList<Monster> wave4;
    protected ArrayList<Monster> wave5;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

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
        wave2.add(new Monster(gp, 415 + 115, 50 + 110));

        waves.add(wave2);
    
        // WAVE 3
        wave3 = new ArrayList<Monster>();
        wave3.add(new Monster(gp, 415, 50 + 110));

        waves.add(wave3);
    
        // WAVE 4
        wave4 = new ArrayList<Monster>();
        wave4.add(new Monster(gp, 415 + 115 * 2, 50 + 110));

        waves.add(wave4);
    
        // WAVE 5
        wave5 = new ArrayList<Monster>();
        wave5.add(new Monster(gp, 415 + 115, 50 + 110 * 2));
        
        waves.add(wave5);
    }

    public ArrayList<Monster> getCurrentWaveMonsters() {

        if (gp.waveNumber < waves.size()) {

            return waves.get(gp.waveNumber - 1);
        }
        return new ArrayList<>();
    }

    public void advanceToNextWave() {

        if (gp.waveNumber < waves.size()) {

            gp.waveNumber ++;
        }
    }
}
