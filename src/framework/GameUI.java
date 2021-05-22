package framework;

import sokoban.objects.CusObj;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

public abstract class GameUI extends JComponent {


    public static final int SHOW_MAIN_MENU = 0;
    public static final int SHOW_SELECTION = 1;
    public static final int SHOW_GAME = 2;

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

    public abstract String getTitle();
    public abstract String getVersion();
    public abstract String getCopyrightNotice();
    public abstract Font getFont();

    // Abstract classes for main menu
    public abstract String[] getMainMenuOptions();
    public abstract BufferedImage getMainMenuBackground();
    public abstract BufferedImage getMainMenuPositionImage();

    // Abstract classes selection
    public abstract String getSelectionTitle();
    public abstract String[] getSelectionOptions();
    public abstract String getSelectionBottomBarText();
    public abstract BufferedImage getSelectionBackground();

    // Abstract classes for the game



    private int position;
    private int selectWindow;

    public GameUI(int selectWindow) {
        this.selectWindow = selectWindow;
        position = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        switch (selectWindow) {
            case SHOW_MAIN_MENU -> mainMenu(g2);
            case SHOW_SELECTION -> selection(g2);
        }

    }

    public int getSelectIndex() {
        return position;
    }

    public void goUp() {
        switch (selectWindow) {
            case SHOW_MAIN_MENU, SHOW_SELECTION -> {
                if (position - 1 >= 0) {
                    position--;
                }
                repaint();
            }
        }
    }

    public void goDown() {
        switch (selectWindow) {
            case SHOW_MAIN_MENU:
                if (position + 1 < getMainMenuOptions().length) {
                    position++;
                }
                break;
            case SHOW_SELECTION:
                if (position + 1 < getSelectionOptions().length) {
                    position++;
                }
                break;
        }
        repaint();
    }

    public void setWindow(int selectWindow) {
        this.selectWindow = selectWindow;
        position = 0;
        repaint();
    }

    public int getWindow() {
        return selectWindow;
    }

    public void refresh() {
        repaint();
    }

    // --- Private methods ---

    /**
     * @param g2
     */
    private void mainMenu(Graphics2D g2) {
        paintBackground(g2, getMainMenuBackground());   // Paint the background

        String title = getTitle().toUpperCase(Locale.ROOT);
        String[] options = getMainMenuOptions();
        Font font = getFont();
        String versionNumber = getVersion().toUpperCase(Locale.ROOT);
        String copyRightNotice = getCopyrightNotice().toUpperCase(Locale.ROOT);

        Font titleFont = font.deriveFont(70f);
        Font optionsFont = font.deriveFont(20f);
        Font optionsSelectedFont = font.deriveFont(20f).deriveFont(Font.BOLD);
        Font smallFont = font.deriveFont(10f);

        // Prints out the title
        g2.setFont(titleFont);
        g2.setColor(Color.white);

        // Calculates the center of the window to get the font in center
        Rectangle2D rectangle2D = titleFont.getStringBounds(title, FONT_RENDER_CONTEXT);
        int rWidth = (int) Math.round(rectangle2D.getWidth());
        int rHeight;

        g2.drawString(title, (getWidth() / 2) - (rWidth / 2), (int) (getHeight() * 0.2));

        // Prints out the selection
        g2.setFont(optionsFont);
        int optionsStartHeight = (int) (getHeight() * 0.4);

        for (int i = 0; i < options.length; i++) {
            if (i == position) {
                g2.setFont(optionsSelectedFont);
                rectangle2D = optionsSelectedFont.getStringBounds(options[i], FONT_RENDER_CONTEXT);

            }
            else {
                rectangle2D = optionsFont.getStringBounds(options[i], FONT_RENDER_CONTEXT);
            }

            rWidth = (int) Math.round(rectangle2D.getWidth());
            rHeight = (int) Math.round(rectangle2D.getHeight());
            g2.drawString(options[i].toUpperCase(Locale.ROOT), (getWidth() / 2) - (rWidth / 2), optionsStartHeight);

            if (i == position) {
                // Draw the character
                g2.drawImage(getMainMenuPositionImage(), null, (getWidth() / 2) - (rWidth / 2) - 50,
                        optionsStartHeight - getMainMenuPositionImage().getHeight() + 5);

                g2.setFont(optionsFont);     // Select back the default font again
            }

            optionsStartHeight += (rHeight + 5);
        }

        g2.setFont(smallFont);

        rectangle2D = smallFont.getStringBounds(copyRightNotice, FONT_RENDER_CONTEXT);
        rWidth = (int) Math.round(rectangle2D.getWidth());

        g2.drawString(versionNumber, 0, getHeight());
        g2.drawString(copyRightNotice, getWidth() - rWidth ,getHeight());
    }

    private void selection(Graphics2D g2) {
        Font font = getFont();
        String[] selections = getSelectionOptions();

        paintBackground(g2, getSelectionBackground());      // Draw background
        drawBottomBar(g2);                                  // Draw the bottom bar
        drawGrayBox(g2, (getWidth() / 2) + 20, 20, (getWidth() / 2) - 40, getHeight()- 90);

        g2.setFont(font.deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString(getSelectionBottomBarText(), 10, getHeight() - 15);

        g2.setFont(font.deriveFont(40f));
        g2.drawString(getSelectionTitle(), 20, 100);

        int y = 65;
        Font fontMenu = font.deriveFont(20f);
        Dimension dimension;

        // Calculate how many can be selected
        Dimension dimensionOfAString = calculateStringDimensions(selections[0], fontMenu);
        int entriesThatCanFit = (int) (((getHeight() - 90) - 20) / (dimensionOfAString.getHeight() - 20)) - 1;

        // I really don't like this implementation but I don't have time to fix it...
        if (position > entriesThatCanFit) {
            for (int i = (position - entriesThatCanFit); i < position + entriesThatCanFit; i++) {
                String content = selections[i].toUpperCase();
                if (i == position) {
                    fontMenu = font.deriveFont(20f).deriveFont(Font.BOLD);     // Use bold font
                    content = "- " + content;
                }

                dimension = drawString(g2, content, fontMenu, (getWidth() / 2) + 30, y);
                y += dimension.getHeight() - 20;
                fontMenu = font.deriveFont(20f);

                if (i - (position - entriesThatCanFit) >= entriesThatCanFit) {
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < selections.length; i++) {
                String content = selections[i].toUpperCase();
                if (i == position) {
                    fontMenu = font.deriveFont(20f).deriveFont(Font.BOLD);     // Use bold font
                    content = "- " + content;
                }

                dimension = drawString(g2, content, fontMenu, (getWidth() / 2) + 30, y);
                y += dimension.getHeight() - 20;
                fontMenu = font.deriveFont(20f);

                if (i >= entriesThatCanFit) {
                    break;
                }
            }
        }
    }

    // --- Private methods that are used to simplify drawing ---

    /**
     * Paints the background that is selected in the BufferedImage 'backgroundImage' variable. This should be run
     * when the window size gets enlarged or shrank (maybe not necessary when shrank)
     *
     * @param g Graphics2D
     */
    private void paintBackground(Graphics2D g, BufferedImage backgroundImage) {
        int width = backgroundImage.getWidth();
        int height = backgroundImage.getHeight();

        for (int i = 0; i < (getHeight() / width) + 1; i++) {
            for (int j = 0; j < (getWidth() / width) + 1; j++) {
                g.drawImage(backgroundImage, null, (j * width), (i * height));
            }
        }
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
     * Paints over the whole screen a 70% transparent gray background. This is used when drawing information that
     * is important and so that the player is not seeing the game as much
     *
     * @param g2
     * @param xFrom
     * @param yFrom
     * @param xTo
     * @param yTo
     */
    public void drawGrayBox(Graphics2D g2, int xFrom, int yFrom, int xTo, int yTo) {
        g2.setColor(new Color(0, 0,0,0.7f));
        g2.fillRect(xFrom, yFrom, xTo, yTo);
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
}

