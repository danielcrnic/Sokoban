package sokoban.objects.boxes;

import sokoban.objects.CusObj;

import java.awt.*;

import static sokoban.SokobanSecond.GameDrawer.TEXTURE_CIRCLE;
import static sokoban.SokobanSecond.GameDrawer.TEXTURE_CIRCLE_MARKED;


public class CircleBox extends CusObj {

    private static final int defaultTexture = TEXTURE_CIRCLE;
    private static final int triggerTexture = TEXTURE_CIRCLE_MARKED;

    public CircleBox(int x, int y, Color color) {
        super(x, y, defaultTexture, triggerTexture, color);
    }

    public CircleBox(int x, int y) {
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
        return "CIRCLE";
    }
}
