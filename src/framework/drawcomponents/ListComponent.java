package framework.drawcomponents;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Locale;

public abstract class ListComponent extends Component {

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

    /**
     * @return Returns the selection that is currently selected
     */
    public int getSelection() {
        return selection;
    }

    /**
     * Called to move the selection up, this will also repaint the component
     */
    public void selectionMoveUp() {
        if (selection - 1 >= 0) {
            selection--;
            repaint();
        }

    }

    /**
     * Called to move the selection down, this will also repaint the component
     */
    public void selectionMoveDown() {
        if (selection + 1 < getOptions().length) {
            selection++;
            repaint();
        }

    }
}