package framework.drawcomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

public abstract class MenuComponent extends JComponent {

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

    public abstract Font getFont();

    public abstract String getTitle();
    public abstract String getVersion();
    public abstract String getCopyrightNotice();

    // Abstract classes for main menu
    public abstract String[] getMainMenuOptions();
    public abstract BufferedImage getMainMenuBackground();
    public abstract BufferedImage getMainMenuPositionImage();

    private int selection;

    public MenuComponent() {
        selection = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

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
            if (i == selection) {
                g2.setFont(optionsSelectedFont);
                rectangle2D = optionsSelectedFont.getStringBounds(options[i], FONT_RENDER_CONTEXT);

            } else {
                rectangle2D = optionsFont.getStringBounds(options[i], FONT_RENDER_CONTEXT);
            }

            rWidth = (int) Math.round(rectangle2D.getWidth());
            rHeight = (int) Math.round(rectangle2D.getHeight());
            g2.drawString(options[i].toUpperCase(Locale.ROOT), (getWidth() / 2) - (rWidth / 2), optionsStartHeight);

            if (i == selection) {
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
        g2.drawString(copyRightNotice, getWidth() - rWidth, getHeight());
    }

    public int getSelection() {
        return selection;
    }

    public void selectionMoveUp() {
        if (selection - 1 >= 0) {
            selection--;
            repaint();
        }
    }

    public void selectionMoveDown() {
        if (selection + 1 < getMainMenuOptions().length) {
            selection++;
            repaint();
        }
    }

    public void setSelection(int selection) {
        this.selection = selection;
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
        g2.setColor(new Color(0, 0, 0, 0.2f));
        g2.fillRect(0, getHeight() - 50, getWidth(), 50);
    }

    /**
     * @param g2
     */
    public void drawTopBar(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 0.2f));
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
        g2.setColor(new Color(0, 0, 0, 0.7f));
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
        g2.drawString(string, x, y);
        return new Dimension((int) Math.round(rectangle2D.getWidth()), (int) Math.round(rectangle2D.getHeight()));
    }

    /**
     * Paints the background that is selected in the BufferedImage 'backgroundImage' variable. This should be run
     * when the window size gets enlarged or shrank (maybe not necessary when shrank)
     *
     * @param g2     Graphics2D
     * @param color1 The color to be painted
     * @param color2 The color to be painted
     */
    private void paintColorBackground(Graphics2D g2, Color color1, Color color2) {
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}

