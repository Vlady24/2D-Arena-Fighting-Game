package main;

import entity.Entity;
import entity.Monster;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Class for checking the collision between player/monsters and solid blocks.
 * 
 * Also checks the collison between monsters and player, monsters and player sword and monsters
 * and monsters.
 */
public class CollisionChecker {

    private GamePanel gp;

    /**
     * Class constructor.
     * 
     * @param gp gamePanel
     */
    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    /**
     * Method used for checking collision between an entity and solid blocks.
     * 
     * @param entity (monster or player)
     */
    public void checkTile(Entity entity) {
        
        // CALCULATES THE COORDINATES OF THE ENTITY'S HITBOX
        int entityLeftX = (int) entity.getX() + entity.getCollisionArea().x;
        int entityRightX = (int) entity.getX() + entity.getCollisionArea().x 
                                                                + entity.getCollisionArea().width;
        int entityTopY = (int) entity.getY() + entity.getCollisionArea().y;
        int entityBottomY = (int) entity.getY() + entity.getCollisionArea().y 
                                                                + entity.getCollisionArea().height;

        int entityLeftCol = entityLeftX / gp.getTileSize();
        int entityRightCol = entityRightX / gp.getTileSize();
        int entityTopRow = entityTopY / gp.getTileSize();
        int entityBottomRow = entityBottomY / gp.getTileSize();
        
        int tileNum1;
        int tileNum2;
        
        // STOPS THE PLAYER FROM MOVING IF THE NEXT BLOCK IN HIS DIRECTION OF MOVEMENT IS SOLID
        switch (entity.getDirection()) {

            case "up":
                entityTopRow = (entityTopY - entity.getSpeed()) / gp.getTileSize();

                // Up-left corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityTopRow);
                
                // Up-right corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityTopRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.setCollisionBlockOn(true);
                }

                break;
            
            case "down":
                entityBottomRow = (entityBottomY + 10 + entity.getSpeed()) / gp.getTileSize();

                // Down-left corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityBottomRow);
                
                // Down-right corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityBottomRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.setCollisionBlockOn(true);
                }
                
                break;

            case "left":
                entityLeftCol = (entityLeftX - entity.getSpeed()) / gp.getTileSize();

                // Up-left corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityTopRow);
                
                // Down-left corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityBottomRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.setCollisionBlockOn(true);
                }
                
                break;
                
            case "right":
                entityRightCol = (entityRightX + entity.getSpeed()) / gp.getTileSize();

                // Up-right corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityTopRow);
                
                // Down-right corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityBottomRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.setCollisionBlockOn(true);
                }
                
                break;

            default:

                break;   
        }
    }

    /**
     * Method used for checking collision between monster and player.
     * 
     * @param player player
     */
    public void checkMonsterWithPlayer(Entity player) {
        
        ArrayList<Monster> monsters = gp.aSetter.getCurrentWaveMonsters();

        for (int i = 0; i < monsters.size(); i++) {

            Monster monster = monsters.get(i);

            // CHECKING THE COLLISON IF THE MONSTER IS STILL ALIVE
            if (monster != null) {

                Rectangle playerCollisionArea = new Rectangle((int) player.getX(), 
                                                              (int) player.getY(),
                                                              player.getCollisionArea().width,
                                                              player.getCollisionArea().height);

                Rectangle monsterCollisionArea = new Rectangle((int) monster.getX(), 
                                                              (int) monster.getY(),
                                                              monster.getCollisionArea().width,
                                                              monster.getCollisionArea().height);

                if (playerCollisionArea.intersects(monsterCollisionArea)) {

                    player.setCollisionMonstersOn(true);

                    if (player.getDirection().equals("up") && player.getY() > monster.getY()) {

                        player.setY(player.getY() + player.getSpeed());
                        
                    } else if (player.getDirection().equals("down") 
                               && player.getY() < monster.getY()) {
                        
                        player.setY(player.getY() - player.getSpeed());

                    } else if (player.getDirection().equals("left") 
                               && player.getX() > monster.getX()) {
                        
                        player.setX(player.getX() + player.getSpeed());
                        
                    } else if (player.getDirection().equals("right") 
                               && player.getX() < monster.getX()) {
                        
                        player.setX(player.getX() - player.getSpeed());
                    }
                }

            }
        }
    }

    /**
     * Method used for checking collison between a monster and other monster.
     */
    public void checkMonsterWithMonster() {

        ArrayList<Monster> monsters = gp.aSetter.getCurrentWaveMonsters();

        // CHECKS COLLISION BETWEEN EACH MONSTER IN THE CURRENT WAVE
        for (int i = 0; i < monsters.size(); i++) {

            Monster monster = monsters.get(i);

            // CHECKS THE COLLISION IF THE MONSTER IS STILL ALIVE
            if (monster != null) {

                Rectangle monsterCollisionArea = new Rectangle((int) monster.getX(), 
                                                               (int) monster.getY(),
                                                              monster.getCollisionArea().width,
                                                              monster.getCollisionArea().height);

                for (int j = i + 1; j < monsters.size(); j++) {

                    Monster nextMonster = monsters.get(j);

                    // CHECKS COLLISION IF MONSTER IS STILL ALIVE
                    if (nextMonster != null) {

                        Rectangle nextMonsterCollisionArea = new Rectangle(
                                                                        (int) nextMonster.getX(), 
                                                                        (int) nextMonster.getY(),
                                                              nextMonster.getCollisionArea().width,
                                                            nextMonster.getCollisionArea().height);

                        if (monsterCollisionArea.intersects(nextMonsterCollisionArea)) {

                            monster.setCollisionMonstersOn(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * Method used for checking if a monster was hit by player's sword.
     * 
     * Returns i (monster index) or -1 if monster was not hit by the sword.
     * 
     * @param player player
     */
    public int checkMonsterWithSword(Entity player) {
        
        ArrayList<Monster> monsters = gp.aSetter.getCurrentWaveMonsters();

        // CHECKS HITBOX COLLISON WITH EACH MONSTER
        for (int i = 0; i < monsters.size(); i++) {

            Monster monster = monsters.get(i);

            // CHECKS COLLISION IF MONSTER IS STILL ALIVE
            if (monster != null) {

                Rectangle playerCollisionArea = new Rectangle((int) player.getX(), 
                                                              (int) player.getY(),
                                                              player.getCollisionArea().width,
                                                              player.getCollisionArea().height);

                Rectangle monsterCollisionArea = new Rectangle((int) monster.getX(), 
                                                               (int) monster.getY(),
                                                              monster.getCollisionArea().width,
                                                              monster.getCollisionArea().height);

                // Checks collision between player and monster
                if (playerCollisionArea.intersects(monsterCollisionArea)) {

                    return i;

                }

            }
        }
        return -1;

    }
}
