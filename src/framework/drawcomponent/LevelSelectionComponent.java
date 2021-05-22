package framework.drawcomponent;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelSelectionComponent extends DrawComponent{

    private String[] selections;
    private int position;
    private final BufferedImage bufferedImage;
    private final Font font;

    public LevelSelectionComponent(String[] selections, int position, BufferedImage bufferedImage, Font font) {
        this.selections = selections;
        this.position = position;
        this.bufferedImage = bufferedImage;
        this.font = font.deriveFont(20f);
    }

    public void setPosition(int position) {
        this.position = position;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        paintBackground(g2, bufferedImage);     // Draw background
        drawBottomBar(g2);                      // Draw the bottom bar
        drawGrayBox(g2, (getWidth() / 2) + 20, 20, (getWidth() / 2) - 40, getHeight()- 90);

        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString("ESC: TO GO BACK   ENTER: SELECT", 10, getHeight() - 15);

        g2.setFont(font.deriveFont(70f));
        g2.drawString("SELECT", 20, 100);
        g2.drawString("LEVEL", 20, 200);

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
}
