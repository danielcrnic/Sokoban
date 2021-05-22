package framework.drawcomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

public abstract class ListComponent extends JComponent {

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(null, true, true);

    public abstract Font getFont();

    // Abstract classes selection
    public abstract String getTitle();
    public abstract String[] getOptions();
    public abstract String getBottomBarText();
    public abstract BufferedImage getBackgroundImage();

    private int selection;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Font font = getFont();
        String[] selections = getOptions();

        paintBackground(g2, getBackgroundImage());          // Draw background
        drawBottomBar(g2);                                  // Draw the bottom bar
        drawGrayBox(g2, (getWidth() / 2) + 20, 20, (getWidth() / 2) - 40, getHeight()- 90);

        g2.setFont(font.deriveFont(20f));
        g2.setColor(Color.WHITE);
        g2.drawString(getBottomBarText().toUpperCase(Locale.ROOT), 10, getHeight() - 15);

        g2.setFont(font.deriveFont(40f));
        g2.drawString(getTitle(), 20, 100);

        int y = 65;
        Font fontMenu = font.deriveFont(20f);
        Dimension dimension;

        // Calculate how many can be selected
        Dimension dimensionOfAString = calculateStringDimensions(selections[0], fontMenu);
        int entriesThatCanFit = (int) (((getHeight() - 90) - 20) / (dimensionOfAString.getHeight() - 20)) - 1;

        // I really don't like this implementation but I don't have time to fix it...
        if (selection > entriesThatCanFit) {
            for (int i = (selection - entriesThatCanFit); i < selection + entriesThatCanFit; i++) {
                String content = selections[i].toUpperCase();
                if (i == selection) {
                    fontMenu = font.deriveFont(20f).deriveFont(Font.BOLD);     // Use bold font
                    content = "- " + content;
                }

                dimension = drawString(g2, content, fontMenu, (getWidth() / 2) + 30, y);
                y += dimension.getHeight() - 20;
                fontMenu = font.deriveFont(20f);

                if (i - (selection - entriesThatCanFit) >= entriesThatCanFit) {
                    break;
                }
            }
        }
        else {
            for (int i = 0; i < selections.length; i++) {
                String content = selections[i].toUpperCase();
                if (i == selection) {
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
        if (selection + 1 < getOptions().length) {
            selection++;
            repaint();
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

}