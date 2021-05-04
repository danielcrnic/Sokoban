import java.awt.*;

public class Beacon extends CustomObject {

    public Beacon(int x, int y, Image img) {

        super(x, y, img, false, false);
    }

    public Beacon(int x, int y, Image img, Color clr) {

        super(x, y, img, false, false, clr);
    }
}