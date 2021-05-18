package sokoban.drawcomponent;

import sokoban.objects.CusObj;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameComponent extends JComponent {

    public final static int TEXTURE_PLAYER = 0;
    public final static int TEXTURE_WALL = 1;
    public final static int TEXTURE_FLOOR = 2;
    public final static int TEXTURE_WATER = 3;

    public final static int TEXTURE_STAR = 4;
    public final static int TEXTURE_STAR_HOLE = 5;
    public final static int TEXTURE_STAR_MARKED = 6;

    public final static int TEXTURE_SQUARE = 7;
    public final static int TEXTURE_SQUARE_HOLE = 8;
    public final static int TEXTURE_SQUARE_MARKED = 9;

    public final static int TEXTURE_CIRCLE = 10;
    public final static int TEXTURE_CIRCLE_HOLE = 11;
    public final static int TEXTURE_CIRCLE_MARKED = 12;

    // Change this when adding a new texture!
    private final static int NUMBER_OF_TEXTURES = 13;

    // private CusObj[][] map;
    // private int width;
    // private int height;

    private BufferedImage[] textures;

    public GameComponent(BufferedImage[] textures) throws Exception{
        if (textures.length != NUMBER_OF_TEXTURES) {
            throw new Exception("The number of textures does not line up!");
        }
        else {
            this.textures = textures;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;



        // int x = 0;
        // int y = 0;
        // for (int i = 0; i < map.length; i++) {
        //     for (int j = 0; j < map[0].length; j++) {
        //         g2.drawImage(map[i][j].getTexture(),null, x, y);
        //         x += map[i][j].getTexture().getWidth();
        //     }
        //     x = 0;
        //     y += map[i][0].getTexture().getHeight();
        // }
    }
}
