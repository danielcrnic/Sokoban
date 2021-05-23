package sokoban.objects.holes;

import sokoban.objects.CusObj;

import java.awt.*;

import static sokoban.Sokoban.GameDrawer.TEXTURE_SQUARE_HOLE;

public class SquareHole extends CusObj {

    private static final int defaultTexture = TEXTURE_SQUARE_HOLE;

    public SquareHole(int x, int y, Color color) {
        super(x, y, defaultTexture, -1, color);
    }

    public SquareHole(int x, int y) {
        super(x, y, defaultTexture);
    }


    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isSteppable() {
        return true;
    }

    @Override
    public int typeOfObject() {
        return TYPE_HOLE;
    }

    @Override
    public String objectName() {
        return "SQUARE";
    }
}
