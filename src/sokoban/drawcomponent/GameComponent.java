package sokoban.drawcomponent;

import sokoban.Level;
import sokoban.objects.CusObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
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
    private Timer updateText;
    private Font font;

    private Color color1;
    private Color color2;

    private int secondsPassed;
    private int correctMoves, incorrectMoves, movesPerSecond;
    private int bottomBarText;

    public GameComponent(Level level, BufferedImage[] textures, Font font, Color color1, Color color2) throws Exception{
        if (textures.length != NUMBER_OF_TEXTURES) {
            throw new Exception("The number of textures does not line up!");
        }
        else {
            this.textures = textures;
            this.level = level;
            this.font = font.deriveFont(20f);

            secondsPassed = 0;
            correctMoves = 0;
            incorrectMoves = 0;
            movesPerSecond = 0;
            bottomBarText = 0;

            this.color1 = color1;
            this.color2 = color2;

            updateText = new Timer(10000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    bottomBarText = (bottomBarText + 1) % 3;
                }
            });
            updateText.start();
        }
    }

    /**
     * Call the method when the layout has been modified and has to be repainted
     */
    public void update() {
        repaint();
    }

    public void updateTime(int seconds) {
        secondsPassed = seconds;
        repaint();
    }

    public void stopUpdate() {
        updateText.stop();
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

        // Draw the background
        paintColorBackground(g2, color1, color2);

        // Draw first the layout
        drawLayout(g2, layout);

        // Draw the objects
        drawObjects(g2, width, height, holes);
        drawObjects(g2, width, height, boxes);

        // Lastly, draw the player
        drawObjects(g2, width, height, player);

        // Draw the dashboard
        drawDashboard(g2);
    }

    private void drawDashboard(Graphics2D g2) {
        FontRenderContext fontRenderContext = new FontRenderContext(null, true, true);

        // Top bar
        Color backgroundColor = new Color(0, 0,0,0.2f);
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, getWidth(), 50);

        // Bottom bar
        g2.fillRect(0, getHeight() - 50, getWidth(), 50);

        // Draw the top bar text
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString( "HOLES: "  + level.getNumberOfFilledHoles() + "/" + level.getNumberOfHoles(), 10, 35);

        String timeText = "TIME: " + String.format("%02d", (secondsPassed / 60)) + ":" + String.format("%02d", (secondsPassed % 60));
        Rectangle2D rectangle2D = font.getStringBounds(timeText, fontRenderContext);
        int rWidth = (int) Math.round(rectangle2D.getWidth());

        g2.drawString(timeText,getWidth() - 10 - rWidth, 35);

        String bottomBar;
        // Draw the bottom bar text
        switch (bottomBarText) {
            case 1:
                bottomBar = "MOVES: " + level.getCorrectMoves() + "    INCORRECT MOVES: " + level.getIncorrectMoves() + "    TOTAL MOVES: " + level.getTotalMoves();
                break;
            case 2:
                bottomBar = "MOVES PER SECOND: " + String.format("%.2f", (float) level.getTotalMoves() / (float) secondsPassed) + " MOVES/SEC";
                break;
            default:
                bottomBar = "PRESS ESC TO PAUSE";
                break;
        }

        g2.drawString(bottomBar, 10, getHeight() - 15);
    }

    /**
     * Draws the layout on the screen
     *
     * @param g2 Graphics2D
     * @param layout The layout in a doubly array
     */
    private void drawLayout(Graphics2D g2, CusObj[][] layout) {
        // Get the width and height to be able to draw the level in center
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        int width = layout[0].length * textures[0].getWidth();
        int height = layout.length * textures[0].getHeight();

        int x = (windowWidth / 2) - (width / 2);
        int y = (windowHeight / 2) - (height / 2);

        // Draw first the layout
        for (CusObj[] cusObjs : layout) {
            for (int j = 0; j < cusObjs.length; j++) {
                if (cusObjs[j] != null) {
                    int textureID = cusObjs[j].getTextureNumber();
                    g2.drawImage(textures[textureID], null, x, y);
                    // FIXME: Add also that the Color variable is added in this moment
                }
                x += textures[0].getWidth();
            }
            x = (windowWidth / 2) - (width / 2);
            y += textures[0].getHeight();
        }
    }

    /**
     * Draws objects to the screen
     *
     * @param g2 Graphics2D
     * @param width The width of the "map"
     * @param height The height of the "map"
     * @param objects Objects to be drawn
     */
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

    /**
     * Paints the background that is selected in the BufferedImage 'backgroundImage' variable. This should be run
     * when the window size gets enlarged or shrank (maybe not necessary when shrank)
     *
     * @param g Graphics2D
     * @param color1 The color to be painted
     * @param color2 The color to be painted
     */
    private void paintColorBackground(Graphics2D g, Color color1, Color color2) {
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, getWidth(),getHeight(), color2);
        g.setPaint(gradientPaint);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
