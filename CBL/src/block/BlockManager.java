package block;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 * Class for managing the blocks of the map.
 */
public class BlockManager {

    private GamePanel gp;
    private Block[] block;
    private int[][] mapBlockNum;

    /**
     *  Class constructor.
     * 
     *  Creates a 2D array of numbers which will represent each block.
     * 
     * @param gp GamePanel
     */
    public BlockManager(GamePanel gp) {

        this.gp = gp;

        block = new Block[6];
        mapBlockNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];

        getBlockImage();
        loadMap();
    }

    /**
     *  Method for creating and setting the image of each block from file.
     */
    public void getBlockImage() {

        try {
            
            block[0] = new Block();
            block[0].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/sand.png")));

            block[1] = new Block(true);
            block[1].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/sandwall.png")));

            block[2] = new Block(true);
            block[2].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/magma.png")));

            block[3] = new Block(true);
            block[3].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/crystal.png")));

            block[4] = new Block();
            block[4].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/deadtree.png")));
                                                                    
            block[5] = new Block();
            block[5].setImage(ImageIO.read(getClass().getResourceAsStream(
                                                                    "/mapblocks/deadtree2.png")));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for loading the map by text file input into a matrix.
     */
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

            e.printStackTrace();
        }
    }

    /**
     * Method for drawing the map from the matrix of blocks.
     * @param g2 The Graphics2D object used for drawing.
     */
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

    /**
     * Getter for the block at the specified index.
     * @param tileNum index of the block
     * @return block[tileNum]
     */
    public Block getBlockAtIndex(int tileNum) {

        return block[tileNum];
    }

    /**
     * Getter for the index of the block at the specified position in the matrix.
     * @param i line index
     * @param j column index
     * @return the number of the block in the specified position
     */
    public int getmapBlockNumAtIndexes(int i, int j) {

        return mapBlockNum[i][j];
    }
}
