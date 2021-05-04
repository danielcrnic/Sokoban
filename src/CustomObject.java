import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class CustomObject extends ImageIcon {

    public int xPos;
    public int yPos;
    public Image image;
    public boolean isPushable;
    public boolean isSolid;
    public Color color;

    public CustomObject(int x, int y, Image img,boolean push, boolean solid, Color clr){

        xPos = x;
        yPos = y;
        image = img;
        isPushable = push;
        isSolid = solid;
        color = clr;
    }

    public CustomObject(int x, int y, Image img, boolean push, boolean solid) {
        xPos = x;
        yPos = y;
        image = img;
        isPushable = push;
        isSolid = solid;
    }
}
