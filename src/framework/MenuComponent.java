package framework;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
