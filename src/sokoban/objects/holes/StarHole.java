package sokoban.objects.holes;

import sokoban.objects.CusObj;

import java.awt.*;

import static sokoban.Sokoban.GameDrawer.TEXTURE_STAR_HOLE;

public class StarHole extends CusObj {

    private static final int defaultTexture = TEXTURE_STAR_HOLE;

    public StarHole(int x, int y, Color color) {
        super(x, y, defaultTexture,-1, color);
    }

    public StarHole(int x, int y) {
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
        return "STAR";
    }
}
