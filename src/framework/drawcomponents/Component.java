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
