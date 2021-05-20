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
import java.util.Locale;

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
    private final static int OPTIONS_PADDING = 5;

    public final static int MODE_GAME = 0;
    public final static int MODE_PAUSE = 1;
    public final static int MODE_WIN = 2;
    public final static int MODE_CENTER_TEXT = 3;

    private FontRenderContext fontRenderContext;

    private BufferedImage[] textures;
    private Level level;
    private Timer updateText;
    private Font font;

    private final Color color1;
    private final Color color2;

    private int secondsPassed;
    private int bottomBarText;

    private String[] optionsPause;
    private String[] optionsWin;
    private String centerText;

    private int position, mode;

    public GameComponent(Level level, BufferedImage[] textures, Font font, Color color1, Color color2,
                         String[] optionsPause, String[] optionsWin) throws Exception{
        if (textures.length != NUMBER_OF_TEXTURES) {
            throw new Exception("The number of textures does not line up!");
        }
        else {
            fontRenderContext = new FontRenderContext(null, true, true);

            this.textures = textures;
            this.level = level;
            this.font = font.deriveFont(20f);

            secondsPassed = 0;
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

            this.optionsPause = optionsPause;
            this.optionsWin = optionsWin;

            position = 0;
            mode = MODE_GAME;
            centerText = "HELLO WORLD!";
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

    /**
     * Stops the bottom bar from updating
     */
    public void stopUpdate() {
        updateText.stop();
    }

    /**
     * Starts the bottom bar to update
     */
    public void startUpdate() {
        updateText.start();
    }

    /**
     * Selects the mode that should be used, it can be set to the following:
     *      (MODE_GAME)        -> To only show the game
     *      (MODE_PAUSE)       -> Pause menu
     *      (MODE_WIN)         -> Win menu
     *      (MODE_CENTER_TEXT) -> To show only center text
     *
     * @param mode The mode to be selected
     */
    public void setDisplayMode(int mode) {
        this.mode = mode;
    }

    /**
     * Sets the text for the MODE_CENTER TEXT view
     *
     * @param centerText The new text to be displayed
     */
    public void setCenterText(String centerText) {
        this.centerText = centerText;
    }

    /**
     * Sets the position of the cursor in MODE_PAUSE and MODE_WIN
     *
     * @param position The position of the cursor where 0 is the first selection
     */
    public void setPosition(int position) {
        this.position = position;
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

        // Draw overlay (if there is one)
        switch (mode) {
            case MODE_PAUSE -> paintPauseMenu(g2);
            case MODE_WIN -> paintCompleteMenu(g2);
            case MODE_CENTER_TEXT -> paintMiddleText(g2);
        }
    }

    /**
     * @param g2
     */
    private void drawDashboard(Graphics2D g2) {
        fontRenderContext = new FontRenderContext(null, true, true);

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

        String timeText = "TIME: " + String.format("%02d", (secondsPassed / 60)) + ":" +
                String.format("%02d", (secondsPassed % 60));
        Rectangle2D rectangle2D = font.getStringBounds(timeText, fontRenderContext);
        int rWidth = (int) Math.round(rectangle2D.getWidth());

        g2.drawString(timeText,getWidth() - 10 - rWidth, 35);

        String bottomBar;
        // Draw the bottom bar text
        switch (bottomBarText) {
            case 1:
                bottomBar = "MOVES: " + level.getCorrectMoves() + "    INCORRECT MOVES: " + level.getIncorrectMoves()
                        + "    TOTAL MOVES: " + level.getTotalMoves();
                break;
            case 2:
                bottomBar = "MOVES PER SECOND: " +
                        String.format("%.2f", (float) level.getTotalMoves() / (float) secondsPassed) + " MOVES/SEC";
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
     * @param g2 Graphics2D
     * @param color1 The color to be painted
     * @param color2 The color to be painted
     */
    private void paintColorBackground(Graphics2D g2, Color color1, Color color2) {
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, getWidth(),getHeight(), color2);
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * @param g2
     */
    private void paintGray(Graphics2D g2) {
        g2.setColor(new Color(0, 0,0,0.7f));
        g2.fillRect(0, 0, getWidth(), getHeight());

    }

    /**
     * @param g2
     */
    private void paintPauseMenu(Graphics2D g2) {
        paintGray(g2);
        g2.setColor(Color.WHITE);

        Font titleFont = font.deriveFont(50f);
        Font optionsFont = font.deriveFont(20f);
        Font optionsSelectedFont = font.deriveFont(30f).deriveFont(Font.BOLD);

        String title = "PAUSED";
        g2.setFont(titleFont);
        Rectangle2D rectangle2D = titleFont.getStringBounds(title, fontRenderContext);
        int rWidth = (int) Math.round(rectangle2D.getWidth());
        int rHeight;

        g2.drawString(title, (getWidth() / 2) - (rWidth / 2), (int) (getHeight() * 0.2));

        // Prints out the selection
        g2.setFont(optionsFont);
        int optionsStartHeight = (int) (getHeight() * 0.4);

        for (int i = 0; i < optionsPause.length; i++) {
            if (i == position) {
                g2.setFont(optionsSelectedFont);
                rectangle2D = optionsSelectedFont.getStringBounds(optionsPause[i], fontRenderContext);

            }
            else {
                rectangle2D = optionsFont.getStringBounds(optionsPause[i], fontRenderContext);
            }

            rWidth = (int) Math.round(rectangle2D.getWidth());
            rHeight = (int) Math.round(rectangle2D.getHeight());
            g2.drawString(optionsPause[i].toUpperCase(Locale.ROOT), (getWidth() / 2) - (rWidth / 2), optionsStartHeight);

            g2.setFont(optionsFont);     // Select back the default font again
            optionsStartHeight += (rHeight + OPTIONS_PADDING);
        }

    }

    /**
     * @param g2
     */
    private void paintCompleteMenu(Graphics2D g2) {
        paintGray(g2);
        g2.setColor(Color.WHITE);

        Font titleFont = font.deriveFont(50f);
        Font optionsFont = font.deriveFont(20f);
        Font optionsSelectedFont = font.deriveFont(30f).deriveFont(Font.BOLD);

        String title = "CLEARED!";
        g2.setFont(titleFont);
        Rectangle2D rectangle2D = titleFont.getStringBounds(title, fontRenderContext);
        int rWidth = (int) Math.round(rectangle2D.getWidth());
        int rHeight;

        g2.drawString(title, (getWidth() / 2) - (rWidth / 2), (int) (getHeight() * 0.2));

        g2.setFont(optionsFont);

        String timeText = "TIME: " + String.format("%02d", (secondsPassed / 60)) + ":" +
                String.format("%02d", (secondsPassed % 60));
        String totalMovesText = "TOTAL MOVES: " + level.getTotalMoves();
        String movesText = "CORRECT MOVES: " + level.getCorrectMoves() + "   INCORRECT MOVES: " +
                level.getIncorrectMoves();
        String movesSecText = "MOVES PER SECOND: " +
                String.format("%.2f", (float) level.getTotalMoves() / (float) secondsPassed) + " MOVES/SEC";

        rectangle2D = optionsFont.getStringBounds(timeText, fontRenderContext);
        rWidth = (int) Math.round(rectangle2D.getWidth());
        rHeight = (int) Math.round(rectangle2D.getHeight());
        int descriptionHeight = (int) (getHeight() * 0.3);
        g2.drawString(timeText, (getWidth() / 2) - (rWidth / 2), descriptionHeight);
        descriptionHeight += rHeight - 10;

        rectangle2D = optionsFont.getStringBounds(totalMovesText, fontRenderContext);
        rWidth = (int) Math.round(rectangle2D.getWidth());
        rHeight = (int) Math.round(rectangle2D.getHeight());
        g2.drawString(totalMovesText, (getWidth() / 2) - (rWidth / 2), descriptionHeight);
        descriptionHeight += rHeight - 10;

        rectangle2D = optionsFont.getStringBounds(movesText, fontRenderContext);
        rWidth = (int) Math.round(rectangle2D.getWidth());
        rHeight = (int) Math.round(rectangle2D.getHeight());
        g2.drawString(movesText, (getWidth() / 2) - (rWidth / 2), descriptionHeight);
        descriptionHeight += rHeight - 10;

        rectangle2D = optionsFont.getStringBounds(movesSecText, fontRenderContext);
        rWidth = (int) Math.round(rectangle2D.getWidth());
        rHeight = (int) Math.round(rectangle2D.getHeight());
        g2.drawString(movesSecText, (getWidth() / 2) - (rWidth / 2), descriptionHeight);
        descriptionHeight =+ rHeight - 10;

        // Prints out the selection
        int optionsStartHeight = descriptionHeight + (int) (getHeight() * 0.6);



        for (int i = 0; i < optionsWin.length; i++) {
            if (i == position) {
                g2.setFont(optionsSelectedFont);
                rectangle2D = optionsSelectedFont.getStringBounds(optionsWin[i], fontRenderContext);

            }
            else {
                rectangle2D = optionsFont.getStringBounds(optionsWin[i], fontRenderContext);
            }

            rWidth = (int) Math.round(rectangle2D.getWidth());
            rHeight = (int) Math.round(rectangle2D.getHeight());
            g2.drawString(optionsWin[i].toUpperCase(Locale.ROOT), (getWidth() / 2) - (rWidth / 2), optionsStartHeight);

            g2.setFont(optionsFont);     // Select back the default font again
            optionsStartHeight += (rHeight + OPTIONS_PADDING);
        }
    }

    /**
     * @param g2
     */
    private void paintMiddleText(Graphics2D g2) {
        paintGray(g2);

        Font titleFont = font.deriveFont(70f);

        g2.setFont(titleFont);
        g2.setColor(Color.WHITE);
        Rectangle2D rectangle2D = titleFont.getStringBounds(centerText, fontRenderContext);
        int rWidth = (int) Math.round(rectangle2D.getWidth());

        g2.drawString(centerText, (getWidth() / 2) - (rWidth / 2), (getHeight() / 2));
    }

}
