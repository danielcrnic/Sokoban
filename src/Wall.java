import java.awt.*;

public class Wall extends CustomObject{

    public Wall(int x, int y, Image img) {

        super(x, y, img, false, true);
    }

    public Wall(int x, int y, Image img, Color clr) {

        super(x, y, img, false, false, clr);
    }
}
