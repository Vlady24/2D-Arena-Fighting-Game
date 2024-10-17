package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import entity.Entity;
import entity.Monster;

/**
 * Class for checking the collision.
 */
public class CollisionChecker {

    private GamePanel gp;

    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        
        int entityLeftX = entity.x + entity.getCollisionArea().x;
        int entityRightX = entity.x + entity.getCollisionArea().x + entity.getCollisionArea().width;
        int entityTopY = entity.y + entity.getCollisionArea().y;
        int entityBottomY = entity.y + entity.getCollisionArea().y 
                                                                + entity.getCollisionArea().height;

        int entityLeftCol = entityLeftX / gp.getTileSize();
        int entityRightCol = entityRightX / gp.getTileSize();
        int entityTopRow = entityTopY / gp.getTileSize();
        int entityBottomRow = entityBottomY / gp.getTileSize();
        
        int tileNum1;
        int tileNum2;
        
        switch (entity.direction) {

            case "up":
                entityTopRow = (entityTopY - entity.speed) / gp.getTileSize();

                // Up-left corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityTopRow);
                
                // Up-right corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityTopRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.collisionBlockOn = true;
                }

                break;
            
            case "down":
                entityBottomRow = (entityBottomY + 10 + entity.speed) / gp.getTileSize();

                // Down-left corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityBottomRow);
                
                // Down-right corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityBottomRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.collisionBlockOn = true;
                }
                
                break;

            case "left":
                entityLeftCol = (entityLeftX - entity.speed) / gp.getTileSize();

                // Up-left corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityTopRow);
                
                // Down-left corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityBottomRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.collisionBlockOn = true;
                }
                
                break;
                
            case "right":
                entityRightCol = (entityRightX + entity.speed) / gp.getTileSize();

                // Up-right corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityTopRow);
                
                // Down-right corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityBottomRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.collisionBlockOn = true;
                }
                
                break;   
        }
    }

    public void checkMonsters(Entity player) {
        
        ArrayList<Monster> monsters = gp.aSetter.getCurrentWaveMonsters();

        for (int i = 0; i < monsters.size(); i++) {

            Monster monster = monsters.get(i);

            if (monster != null) {

                Rectangle playerCollisionArea = new Rectangle(player.x, player.y,
                                                              player.getCollisionArea().width,
                                                              player.getCollisionArea().height);

                Rectangle monsterCollisionArea= new Rectangle(monster.x, monster.y,
                                                              monster.getCollisionArea().width,
                                                              monster.getCollisionArea().height);

                // Checks collision between player and monster
                if (playerCollisionArea.intersects(monsterCollisionArea)) {

                    player.collisionMonstersOn = true;

                    if (player.direction.equals("up") && player.y > monster.y) {
                        player.y += player.speed;
                    } else if (player.direction.equals("down") && player.y < monster.y) {
                        player.y -= player.speed;
                    } else if (player.direction.equals("left") && player.x > monster.x) {
                        player.x += player.speed;
                    } else if (player.direction.equals("right") && player.x < monster.x) {
                        player.x -= player.speed;
                    }
                }

                // Checks collison between monster and all other monsters in current wave
                for (int j = i + 1; j < monsters.size(); j++) {

                    Monster nextMonster = monsters.get(j);

                    if (nextMonster != null) {

                        Rectangle nextMonsterCollisionArea = new Rectangle(nextMonster.x, nextMonster.y,
                                                              nextMonster.getCollisionArea().width,
                                                              nextMonster.getCollisionArea().height);

                        if (monsterCollisionArea.intersects(nextMonsterCollisionArea)) {

                            monster.collisionMonstersOn = true;
                        }
                    }
                }

                checkTile(monster);
            }
        }
    }
}
