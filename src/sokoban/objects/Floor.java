package sokoban.objects;

import java.awt.*;
import java.io.File;

public class Floor implements CusObj {

    public Floor() {

    }

    @Override
    public File getFile() {
        return new File("textures/blank.png");
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
        return true;
    }
}
