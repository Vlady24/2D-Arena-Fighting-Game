package main;

import entity.Entity;

public class CollisionChecker {

    private GamePanel gp;

    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        
        int entityLeftX = entity.x + entity.getCollisionArea().x;
        int entityRightX = entity.x + entity.getCollisionArea().x + entity.getCollisionArea().width;
        int entityTopY = entity.y + entity.getCollisionArea().y;
        int entityBottomY = entity.y + entity.getCollisionArea().y + entity.getCollisionArea().height;

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

                    entity.collisionOn = true;
                }

                break;
            
            case "down":
                entityBottomRow = (entityBottomY + entity.speed) / gp.getTileSize();

                // Down-left corner of hitbox
                tileNum1 = gp.getBlockM().getmapBlockNumAtIndexes(entityLeftCol, entityBottomRow);
                
                // Down-right corner of hitbox
                tileNum2 = gp.getBlockM().getmapBlockNumAtIndexes(entityRightCol, entityBottomRow);

                if (gp.getBlockM().getBlockAtIndex(tileNum1).getCollision() 
                    || gp.getBlockM().getBlockAtIndex(tileNum2).getCollision()) {

                    entity.collisionOn = true;
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

                    entity.collisionOn = true;
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

                    entity.collisionOn = true;
                }
                
                break;

                
        }
    }
}
