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

        block = new Block[11];
        mapBlockNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];

        getBlockImage();
        loadMap();
    }

    public void getBlockImage() {

        try {
            
            block[0] = new Block();
            block[0].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/0stone.png")));

            block[1] = new Block(true);
            block[1].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/1wall.png")));

            block[2] = new Block(true);
            block[2].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/2lava.png")));

            block[3] = new Block(true);
            block[3].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/3fenceoriz.png")));

            block[4] = new Block(true);
            block[4].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/4fencevert.png")));
            
            block[5] = new Block(true);
            block[5].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/5fenceleft.png")));

            block[6] = new Block(true);
            block[6].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/6fenceright.png")));

            block[7] = new Block(true);
            block[7].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/7walltorch.png")));

            block[8] = new Block();
            block[8].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/8bridgedown.png")));

            block[9] = new Block();
            block[9].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/9bridgeup.png")));

            block[10] = new Block(true);
            block[10].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/10coffin.png")));

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
