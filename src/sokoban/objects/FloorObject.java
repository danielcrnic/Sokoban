package sokoban.objects;

import java.awt.*;

import static sokoban.objects.CusObj.TYPE_CUSTOM;

public class FloorObject extends CusObj {

    public FloorObject(int x, int y, int texture, int triggerTexture, Color color) {
        super(x, y, texture, triggerTexture, color);
    }

    public FloorObject(int x, int y, int texture) {
        super(x, y, texture);
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
        return TYPE_CUSTOM;
    }

    @Override
    public String objectName() {
        return null;
    }

}
