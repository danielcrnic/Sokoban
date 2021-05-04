import java.awt.*;

public class Box extends CustomObject{

    public Box(int x, int y, Image img) {

        super(x, y, img, true, true);


    }

    public Box(int x, int y, Image img, Color clr) {

        super(x, y, img, false, false, clr);
    }
}
