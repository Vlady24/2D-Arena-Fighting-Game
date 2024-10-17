package block;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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

    private BufferedImage mapImage;

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
                                                                    "/mapblocks/sand2.png")));

            block[1] = new Block(true);
            block[1].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/sandwall3.png")));

            block[2] = new Block(true);
            block[2].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/magma.png")));

            block[3] = new Block(true);
            block[3].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/crystal.png")));

            block[4] = new Block(true);
            block[4].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/cactus.png")));

            block[5] = new Block();
            block[5].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/deadtree.png")));
                                                                    
            block[6] = new Block();
            block[6].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/deadtree2.png")));   
                                                                    
            block[7] = new Block();
            block[7].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/spawn.png")));                                                        

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
        /*  
        try {
            mapImage = ImageIO.read(getClass().getResourceAsStream("/maps/map2.png"));
            g2.drawImage(mapImage, 0, 0, 1152, 864, null);
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        */
    }

    public Block getBlockAtIndex(int tileNum) {

        return block[tileNum];
    }

    public int getmapBlockNumAtIndexes(int i, int j) {

        return mapBlockNum[i][j];
    }
}
