import javax.imageio.ImageIO;
import java.awt.*;


public class Box {

    public int xPos;
    public int yPos;
    public BufferedImage image;

    public Box(int x, int y, BufferedImage img){

        xPos = x;
        yPos = y;
        image = img;

    }


    public Box(int x, int y){

        xPos = x;
        yPos = y;
        image = ImageIO.read("crate.png");


    }
}
