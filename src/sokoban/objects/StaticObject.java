package sokoban.objects;

import java.awt.*;

public class StaticObject extends CusObj{

    public StaticObject(int x, int y, int texture, int triggerTexture, Color color) {
        super(x, y, texture, triggerTexture, color);
    }

    public StaticObject(int x, int y, int texture, int triggerTexture) {
        super(x, y, texture, triggerTexture);
    }

    public StaticObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isSteppable() {
        return false;
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
