package sokoban.drawcomponent;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class MainMenuComponent extends JComponent {

    private final static int OPTIONS_PADDING = 5;

    private String title;
    private String[] options;
    private String copyRightNotice;
    private String versionNumber;
    private int position;

    private BufferedImage backgroundImage;
    private BufferedImage selectionImage;

    public MainMenuComponent(String title, String[] options, String versionNumber, String copyRightNotice, int position, BufferedImage backgroundImage,
                             BufferedImage selectionImage) {
        this.title = title.toUpperCase(Locale.ROOT);
        this.options = options;
        this.versionNumber = versionNumber.toUpperCase(Locale.ROOT);
        this.copyRightNotice = copyRightNotice.toUpperCase(Locale.ROOT);

        this.backgroundImage = backgroundImage;
        this.selectionImage = selectionImage;

        if (position < 0 || position >= options.length) {
            this.position = 0;
        } else {
            this.position = position;
        }
    }

    /**
     * Marks the selection that the end user wants
     *
     * @param position The position where 0 is the first one.
     */
    public void markSelection(int position) {
        if (position < 0 || position >= options.length) {
            System.err.println( position + " is an incorrect selection made!");
        } else {
            this.position = position;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        paintBackground(g2);     // TODO: Call this method only if the window size has been modified
        paintContent(g2);
    }

    /**
     * Paints the background that is selected in the BufferedImage 'backgroundImage' variable. This should be run
     * when the window size gets enlarged or shrank (maybe not necessary when shrank)
     *
     * @param g Graphics2D
     */
    private void paintBackground(Graphics2D g) {
        BufferedImage background = backgroundImage;

        int width = background.getWidth();
        int height = background.getHeight();

        for (int i = 0; i < (getHeight() / width) + 1; i++) {
            for (int j = 0; j < (getWidth() / width) + 1; j++) {
                g.drawImage(background, null, (j * width), (i * height));
            }
        }

    }
    
    /**
     * This method paints the content, i.e. the title text, the selections that can be selected and also the highlighted
     * selection that the user has selected. This method should be runned after paintBackground() method has been 
     * called first (Or else it print the text and then lay over the background). 
     * 
     * @param g Graphics2D
     */
    private void paintContent(Graphics2D g) {
        FontRenderContext fontRenderContext = new FontRenderContext(null, true, true);

        // FIXME: We cannot load the font here, that is the work of the framework!
        Font titleFont;
        Font optionsFont;
        Font optionsSelectedFont;
        Font smallFont;
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("textures/fonts/Pixeled.ttf")).deriveFont(70f);
            optionsFont = titleFont.deriveFont(20f);
            optionsSelectedFont = titleFont.deriveFont(20f).deriveFont(Font.BOLD);
            smallFont = titleFont.deriveFont(10f);
        } catch (IOException | FontFormatException e) {
            titleFont = new Font("Comic Sans MS", Font.BOLD, 70);
            optionsFont = new Font("Comic Sans MS", Font.PLAIN, 30);
            optionsSelectedFont = new Font("Comic Sans MS", Font.BOLD, 30);
            smallFont = new Font("Comic Sans MS", Font.PLAIN, 10);
            e.printStackTrace();
        }

        // Prints out the title
        g.setFont(titleFont);
        g.setColor(Color.white);

        // Calculates the center of the window to get the font in center
        Rectangle2D rectangle2D = titleFont.getStringBounds(title, fontRenderContext);
        int rWidth = (int) Math.round(rectangle2D.getWidth());
        int rHeight;

        g.drawString(title, (getWidth() / 2) - (rWidth / 2), (int) (getHeight() * 0.2));

        // Prints out the selection
        g.setFont(optionsFont);
        int optionsStartHeight = (int) (getHeight() * 0.4);

        for (int i = 0; i < options.length; i++) {
            if (i == position) {
                g.setFont(optionsSelectedFont);
                rectangle2D = optionsSelectedFont.getStringBounds(options[i], fontRenderContext);

            }
            else {
                rectangle2D = optionsFont.getStringBounds(options[i], fontRenderContext);
            }

            rWidth = (int) Math.round(rectangle2D.getWidth());
            rHeight = (int) Math.round(rectangle2D.getHeight());
            g.drawString(options[i].toUpperCase(Locale.ROOT), (getWidth() / 2) - (rWidth / 2), optionsStartHeight);

            if (i == position) {
                // Draw the character
                g.drawImage(selectionImage, null, (getWidth() / 2) - (rWidth / 2) - 50,
                        optionsStartHeight - selectionImage.getHeight() + 5);

                g.setFont(optionsFont);     // Select back the default font again
            }

            optionsStartHeight += (rHeight + OPTIONS_PADDING);
        }

        g.setFont(smallFont);

        rectangle2D = smallFont.getStringBounds(copyRightNotice, fontRenderContext);
        rWidth = (int) Math.round(rectangle2D.getWidth());

        g.drawString(versionNumber, 0, getHeight());
        g.drawString(copyRightNotice, getWidth() - rWidth ,getHeight());
    }

}

