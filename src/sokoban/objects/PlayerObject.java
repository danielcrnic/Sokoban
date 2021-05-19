package sokoban.objects;

import java.awt.*;

public class PlayerObject extends CusObj{

    public PlayerObject(int x, int y, int texture, int triggerTexture, Color color) {
        super(x, y, texture, triggerTexture, color);
    }

    public PlayerObject(int x, int y, int texture, int triggerTexture) {
        super(x, y, texture, triggerTexture);
    }

    public PlayerObject(int x, int y, int texture) {
        super(x, y, texture);
    }

    @Override
    public boolean isMovable() {
        return true;
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
