package framework.drawcomponents;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;

public abstract class MenuComponent extends Component {

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

}

