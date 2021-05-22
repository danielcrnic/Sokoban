package sokoban.objects.boxes;

import sokoban.objects.CusObj;

import java.awt.*;

import static sokoban.SokobanSecond.GameDrawer.TEXTURE_SQUARE;
import static sokoban.SokobanSecond.GameDrawer.TEXTURE_SQUARE_MARKED;

public class SquareBox extends CusObj {

    private static final int defaultTexture = TEXTURE_SQUARE;
    private static final int triggerTexture = TEXTURE_SQUARE_MARKED;

    public SquareBox(int x, int y, Color color) {
        super(x, y, defaultTexture, triggerTexture, color);
    }

    public SquareBox(int x, int y) {
        super(x, y, defaultTexture, triggerTexture);
    }


    @Override
    public boolean isMovable() {
        return true;
    }

    @Override
    public boolean isSteppable() {
        return false;
    }

    @Override
    public int typeOfObject() {
        return TYPE_BOX;
    }

    @Override
    public String objectName() {
        return "SQUARE";
    }
}
