package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {

        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        
        int entityLeftX = entity.x + entity.collisionArea.x;
        int entityRightX = entity.x + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopY = entity.y + entity.collisionArea.y;
        int entityBottomY = entity.y + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftX / gp.getTileSize();
        int entityRightCol = entityRightX / gp.getTileSize();
        int entityTopRow = entityTopY / gp.getTileSize();
        int entityBottomRow = entityBottomY / gp.getTileSize();
        
        int tileNum1;
        int tileNum2;
        
        switch (entity.direction) {

            case "up":
                entityTopRow = (entityTopY - entity.speed) / gp.getTileSize();
                tileNum1 = gp.blockM.mapBlockNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.blockM.mapBlockNum[entityRightCol][entityTopRow];
                break;
        }
    }
}
