package framework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

public abstract class GameComponent extends JComponent {

    public final static int TEXTURE_NONE = -1;
    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

    public abstract Font getFont();

    public abstract Color getGameBackgroundColor1();
    public abstract Color getGameBackgroundColor2();
    public abstract int getGameBlockWidth();
    public abstract int getGameBlockHeight();
    public abstract int[][] getBackgroundLayout();
    public abstract int[][] getLayer1();
    public abstract int[][] getLayer2();
    public abstract BufferedImage getTexture(int i);

    public abstract String getGameTopBarLeftText();
    public abstract String getGameTopBarMiddleText();
    public abstract String getGameTopBarRightText();
    public abstract String[] getGameBottomBarLeftTexts();
    public abstract int getSecondsBetweenEach();

    public abstract String gamePausedTitle();
    public abstract String[] gamePausedDescription();
    public abstract String[] gamePausedSelections();

    private Timer timer;
    private int lineToShow, selection;
    private boolean paused = false;

    public GameComponent() {
        timer = new Timer(getSecondsBetweenEach() * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lineToShow = lineToShow + 1 % getGameBottomBarLeftTexts().length;

                if (lineToShow >= getGameBottomBarLeftTexts().length) {
                    lineToShow = 0;
                }
            }
        });

        selection = 0;
        lineToShow = 0;
    }

    public void showPauseMenu() {
        paused = true;
        timer.stop();
    }

    public void showGame() {
        paused = false;
        timer.start();
    }

    public void selectionMoveUp() {
        String[] options = gamePausedSelections();
        if (options != null) {
            if (selection - 1 >= 0) {
                selection--;
            }
        }
    }

    public void selectionMoveDown() {
        String[] options = gamePausedSelections();
        if (options != null) {
            if (selection + 1 < options.length) {
                selection++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        drawGame(g2);   // Draw the game

        // Check if the game is paused
        if (paused) {
            drawGamePaused(g2);
        }
    }

    // --- Private methods ---

    /**
     * @param g2
     */
    private void drawGame(Graphics2D g2) {
        // Get the width and height to be able to draw the level in center
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        int[][] backgroundLayout = getBackgroundLayout();
        int[][] layer1 = getLayer1();
        int[][] layer2 = getLayer2();

        paintColorBackground(g2, getGameBackgroundColor1(), getGameBackgroundColor2());
        drawBottomBar(g2);
        drawTopBar(g2);

        Font font = getFont().deriveFont(20f);
        g2.setColor(Color.WHITE);
        // Draw the text
        String text = getGameTopBarLeftText();
        if (text != null) {
            drawString(g2, text, font, 10, 37);
        }
        text = getGameTopBarMiddleText();
        if (text != null) {
            int width = calculateStringDimensions(text, font).width;
            drawString(g2, text, font, (getWidth() / 2) - (width / 2), 37);     // Draw in middle
        }
        text = getGameTopBarRightText();
        if (text != null) {
            int width = calculateStringDimensions(text, font).width;
            drawString(g2, text, font, getWidth() - width - 5 , 37);
        }

        String[] texts = getGameBottomBarLeftTexts();
        drawString(g2, texts[lineToShow], font, 10, getHeight() - 13);

        int width = backgroundLayout[0].length * getTexture(1).getWidth();
        int height = backgroundLayout.length * getTexture(1).getHeight();

        int x = (windowWidth / 2) - (width / 2);
        int y = (windowHeight / 2) - (height / 2);

        for (int i = 0; i < backgroundLayout.length; i++) {
            for (int j = 0; j < backgroundLayout[i].length; j++) {
                if (backgroundLayout[i][j] != TEXTURE_NONE) {
                    g2.drawImage(getTexture(backgroundLayout[i][j]), null, x, y);
                }
                if (layer1[i][j] != TEXTURE_NONE) {
                    g2.drawImage(getTexture(layer1[i][j]), null, x, y);
                }
                if (layer2[i][j] != TEXTURE_NONE) {
                    g2.drawImage(getTexture(layer2[i][j]), null, x, y);
                }
                x += getTexture(1).getWidth();
            }
            x = (windowWidth / 2) - (width / 2);
            y += getTexture(0).getHeight();
        }

    }

    /**
     * @param g2
     */
    public void drawGamePaused(Graphics2D g2) {
        paintGray(g2);
        g2.setColor(Color.WHITE);

        String title = gamePausedTitle();
        String[] description = gamePausedDescription();
        String[] options = gamePausedSelections();

        Font font = getFont();

        Font titleFont = font.deriveFont(50f);
        Font optionsFont = font.deriveFont(20f);
        Font optionsSelectedFont = font.deriveFont(30f).deriveFont(Font.BOLD);

        g2.setFont(titleFont);
        Rectangle2D rectangle2D = titleFont.getStringBounds(title, FONT_RENDER_CONTEXT);
        int rWidth = (int) Math.round(rectangle2D.getWidth());

        g2.drawString(title, (getWidth() / 2) - (rWidth / 2), (int) (getHeight() * 0.13));

        // Prints out the selection
        g2.setFont(optionsFont);
        int height = (int) (getHeight() * 0.2);

        if (description != null) {
            for (int i = 0; i < description.length; i++) {
                    rectangle2D = optionsFont.getStringBounds(description[i].toUpperCase(Locale.ROOT), FONT_RENDER_CONTEXT);
                    rWidth = (int) Math.round(rectangle2D.getWidth());

                    g2.drawString(description[i].toUpperCase(Locale.ROOT), (getWidth() / 2) - (rWidth / 2), height);
                    height = height + (int) Math.round(rectangle2D.getHeight()) - 20;
            }
        }


        // for (int i = 0; i < optionsPause.length; i++) {
        //     if (i == position) {
        //         g2.setFont(optionsSelectedFont);
        //         rectangle2D = optionsSelectedFont.getStringBounds(optionsPause[i], FONT_RENDER_CONTEXT);
//
        //     }
        //     else {
        //         rectangle2D = optionsFont.getStringBounds(optionsPause[i], FONT_RENDER_CONTEXT);
        //     }
//
        //     rWidth = (int) Math.round(rectangle2D.getWidth());
        //     rHeight = (int) Math.round(rectangle2D.getHeight());
        //     g2.drawString(optionsPause[i].toUpperCase(Locale.ROOT), (getWidth() / 2) - (rWidth / 2), optionsStartHeight);
//
        //     g2.setFont(optionsFont);     // Select back the default font again
        //     optionsStartHeight += (rHeight + OPTIONS_PADDING);
        // }
    }

    /**
     * @param g2
     */
    public void drawBottomBar(Graphics2D g2) {
        g2.setColor(new Color(0,0,0,0.2f));
        g2.fillRect(0, getHeight() - 50, getWidth(), 50);
    }

    /**
     * @param g2
     */
    public void drawTopBar(Graphics2D g2) {
        g2.setColor(new Color(0,0,0,0.2f));
        g2.fillRect(0, 0, getWidth(), 50);
    }

    /**
     * Calculates the dimension of the string with the font that will be used
     *
     * @param string
     * @param font
     * @return
     */
    public Dimension calculateStringDimensions(String string, Font font) {
        Rectangle2D rectangle2D = font.getStringBounds(string, FONT_RENDER_CONTEXT);
        return new Dimension((int) Math.round(rectangle2D.getWidth()), (int) Math.round(rectangle2D.getHeight()));
    }

    /**
     * @param g2
     * @param string
     * @param font
     * @param x
     * @param y
     * @return
     */
    public Dimension drawString(Graphics2D g2, String string, Font font, int x, int y) {
        Rectangle2D rectangle2D = font.getStringBounds(string, FONT_RENDER_CONTEXT);

        g2.setFont(font);
        g2.drawString(string, x ,y);
        return new Dimension((int) Math.round(rectangle2D.getWidth()), (int) Math.round(rectangle2D.getHeight()));
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
     * Paints over the whole screen a 70% transparent gray background. This is used when drawing information that
     * is important and so that the player is not seeing the game as much
     *
     * @param g2 Graphics2D
     */
    private void paintGray(Graphics2D g2) {
        g2.setColor(new Color(0, 0,0,0.7f));
        g2.fillRect(0, 0, getWidth(), getHeight());

    }
}
