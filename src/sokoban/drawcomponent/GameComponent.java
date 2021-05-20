package sokoban.drawcomponent;

import sokoban.Level;
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

    private BufferedImage[] textures;
    private Level level;

    public GameComponent(Level level, BufferedImage[] textures) throws Exception{
        if (textures.length != NUMBER_OF_TEXTURES) {
            throw new Exception("The number of textures does not line up!");
        }
        else {
            this.textures = textures;
            this.level = level;
        }
    }

    /**
     * Call the method when the layout has been modified and has to be repainted
     */
    public void update() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        CusObj[][] layout = level.getLayout();
        CusObj[] holes = level.getHoles();
        CusObj[] boxes = level.getBoxes();
        CusObj player = level.getPlayer();

        int width = layout[0].length * textures[0].getWidth();
        int height = layout.length * textures[0].getHeight();

        // Draw first the layout
        drawLayout(g2, layout);

        // Draw the objects
        drawObjects(g2, width, height, holes);
        drawObjects(g2, width, height, boxes);

        // Lastly, draw the player
        drawObjects(g2, width, height, player);
    }

    private void drawLayout(Graphics2D g2, CusObj[][] layout) {
        // Get the width and height to be able to draw the level in center
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        int width = layout[0].length * textures[0].getWidth();
        int height = layout.length * textures[0].getHeight();

        int x = (windowWidth / 2) - (width / 2);
        int y = (windowHeight / 2) - (height / 2);

        // Draw first the layout
        for (int i = 0; i < layout.length; i++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] != null) {
                    int textureID = layout[i][j].getTextureNumber();
                    g2.drawImage(textures[textureID], null, x, y);
                    // FIXME: Add also that the Color variable is added in this moment
                }
                x += textures[0].getWidth();
            }
            x = (windowWidth / 2) - (width / 2);
            y += textures[0].getHeight();
        }
    }

    private void drawObjects(Graphics2D g2, int width, int height, CusObj... objects) {
        // Draw the objects
        for (CusObj o : objects) {
            if (o != null) {
                int x = (getWidth() / 2) - (width / 2) + (o.getX() * textures[0].getWidth());
                int y = (getHeight() / 2) - (height / 2) + (o.getY() * textures[0].getHeight());

                g2.drawImage(textures[o.getTextureNumber()], null, x, y);
            }
        }
    }

}
