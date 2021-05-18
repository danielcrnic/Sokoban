package sokoban.objects;

import java.awt.*;

public class nonInteractiveObject implements CusObj {

    public nonInteractiveObject() {

    }

    @Override
    public int getTextureNumber() {
        return 0;
    }

    @Override
    public Color modifyColor() {
        return null;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isSteppable() {
        return false;
    }
}
