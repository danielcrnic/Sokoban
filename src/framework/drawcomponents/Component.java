package framework.drawcomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Component extends JComponent {

    public static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

    public abstract Font getFont();

    @Override
    protected abstract void paintComponent(Graphics g);

    // --- Public method that makes it easier to draw on the screen ---

    /**
     * Paints the bottom bar which has an dark transparent background. The bottom fills the whole window width but
     * height is only 50px.
     *
     * @param g2 Graphics2D
     */
    public void drawBottomBar(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 0.2f));
        g2.fillRect(0, getHeight() - 50, getWidth(), 50);
    }

    /**
     * Paints the top bar which has an dark transparent background. The top fills the whole window width but height is
     * only 50px.
     *
     * @param g2 Graphics2D
     */
    public void drawTopBar(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 0.2f));
        g2.fillRect(0, 0, getWidth(), 50);
    }

    /**
     * Calculates the dimension of the string with the font that will be used
     *
     * @param string The text that wants to be printed
     * @param font The font what wants to be used
     * @return An Dimension object which contains the width and height of the String that it would require
     */
    public Dimension calculateStringDimensions(String string, Font font) {
        Rectangle2D rectangle2D = font.getStringBounds(string, FONT_RENDER_CONTEXT);
        return new Dimension((int) Math.round(rectangle2D.getWidth()), (int) Math.round(rectangle2D.getHeight()));
    }

    /**
     * Paints a string on the screen with the selected font (the font color has to be defined before). When painted it
     * will return an Dimension object that contains the width and height of the painted text.
     *
     * @param g2 Graphics2D
     * @param string The string to be printed
     * @param font The font to used
     * @param x The x position (Starts at 0 on the left)
     * @param y The y position (Starts at 0 on the top)
     *
     * @return Dimensions of the painted string
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
    public void paintColorBackground(Graphics2D g2, Color color1, Color color2) {
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Paints over the whole screen a 70% transparent gray background. This is used when drawing information that
     * is important and so that the player is not seeing the game as much
     *
     * @param g2 Graphics2D
     */
    public void paintGray(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 0.7f));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Paints over the whole screen a 70% transparent gray background. This is used when drawing information that
     * is important and so that the player is not seeing the game as much
     *
     * @param g2 Graphics2D
     * @param xFrom From x position
     * @param yFrom From y position
     * @param xTo To x position
     * @param yTo To y position
     */
    public void drawGrayBox(Graphics2D g2, int xFrom, int yFrom, int xTo, int yTo) {
        g2.setColor(new Color(0, 0, 0, 0.7f));
        g2.fillRect(xFrom, yFrom, xTo, yTo);
    }

    /**
     * Paints the background that is selected in the BufferedImage 'backgroundImage' variable. This should be run
     * when the window size gets enlarged or shrank (maybe not necessary when shrank)
     *
     * @param g Graphics2D
     */
    public void paintBackground(Graphics2D g, BufferedImage backgroundImage) {
        int width = backgroundImage.getWidth();
        int height = backgroundImage.getHeight();

        for (int i = 0; i < (getHeight() / width) + 1; i++) {
            for (int j = 0; j < (getWidth() / width) + 1; j++) {
                g.drawImage(backgroundImage, null, (j * width), (i * height));
            }
        }
    }

}
