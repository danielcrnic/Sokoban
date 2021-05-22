package framework.drawcomponent;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
    private final Font font;

    public MainMenuComponent(String title, String[] options, String versionNumber, String copyRightNotice, int position,
                             BufferedImage backgroundImage, BufferedImage selectionImage, Font font) {
        this.title = title.toUpperCase(Locale.ROOT);
        this.options = options;
        this.versionNumber = versionNumber.toUpperCase(Locale.ROOT);
        this.copyRightNotice = copyRightNotice.toUpperCase(Locale.ROOT);

        this.backgroundImage = backgroundImage;
        this.selectionImage = selectionImage;
        this.font = font;

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

    public void paint(Graphics g) {
        paintComponent(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        System.out.println(getWidth() + " " + getHeight());

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


    }

}

