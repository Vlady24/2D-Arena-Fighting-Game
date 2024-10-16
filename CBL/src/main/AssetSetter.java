/* 
package main;

import entity.Monster;

public class AssetSetter {
    
    GamePanel gp;

    public AssetSetter(GamePanel gp) {

        this.gp = gp;
    }

    public void setMonster() {

        int x = 415;
        int y = 160;
    
        gp.monster[0] = new Monster(gp, x, y);
    
        x = x + 115;
        gp.monster[1] = new Monster(gp, x, y);
    
        x = x + 115;
        gp.monster[2] = new Monster(gp, x, y);
    
        if (gp.waveNumber >= 2) {
                
            x = 415 + 115;
            y = y + 110;
            gp.monster[3] = new Monster(gp, x, y);
        }
            
        if (gp.waveNumber >= 3) {

            x = 415;
            gp.monster[4] = new Monster(gp, x, y);
        }
            
        if (gp.waveNumber >= 4) {

            x = 415 + 115 * 2;
            gp.monster[5] = new Monster(gp, x, y);
        }
            
        if (gp.waveNumber == 5) {

            x = 415 + 115;
            y = y + 110;
            gp.monster[6] = new Monster(gp, x, y);
        }

    }
}
    */
