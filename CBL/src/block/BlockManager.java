package block;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class BlockManager {

    private GamePanel gp;
    private Block[] block;

    private int[][] mapBlockNum;

    public BlockManager(GamePanel gp) {

        this.gp = gp;

        block = new Block[4];
        mapBlockNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];

        getBlockImage();
        loadMap();
    }

    public void getBlockImage() {

        try {
            
            block[0] = new Block();
            block[0].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/stoneblock.png")));

            block[1] = new Block();
            block[1].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/wallblock.png")));
            block[1].setCollision(true);

            block[2] = new Block();
            block[2].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/lavablock.png")));
            block[2].setCollision(true);

            block[3] = new Block();
            block[3].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/deadtree.png")));
            block[3].setCollision(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {

        try {
            
            InputStream is = getClass().getResourceAsStream("/maps/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {

                String line = br.readLine();
                String[] numbers = line.split(" "); // Reading only the numbers from text file

                while (col < gp.getMaxScreenCol()) {

                    

                    int num = Integer.parseInt(numbers[col]);

                    mapBlockNum[col][row] = num;
                    col++;
                }
                
                if (col == gp.getMaxScreenCol()) {

                    col = 0;
                    row++;
                }

            }

            br.close();

        } catch (Exception e) {
            
        }
    }

    public void draw(Graphics2D g2) {

        int row = 0;
        int col = 0;
        int x = 0;
        int y = 0;

        while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {

            int blockNum = mapBlockNum[col][row];   

            g2.drawImage(block[blockNum].getImage(),
                                                    x, y, gp.getTileSize(), gp.getTileSize(), null);
            col++;
            x = x + gp.getTileSize();

            if (col == gp.getMaxScreenCol()) {
                col = 0;
                x = 0;
                row++;
                y = y + gp.getTileSize();
            }
            
        }

    }

    public Block getBlockAtIndex(int tileNum) {

        return block[tileNum];
    }

    public int getmapBlockNumAtIndexes(int i, int j) {

        return mapBlockNum[i][j];
    }
}
