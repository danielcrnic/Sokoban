import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Box {

    public int xPos;
    public int yPos;
    public Image image;

    public Box(int x, int y, Image img){

        xPos = x;
        yPos = y;
        image = img;

    }


    public Box(int x, int y) throws IOException {

        xPos = x;
        yPos = y;
        image = ImageIO.read(new File("textures/create.png"));



    }
}
