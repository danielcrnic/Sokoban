package sokoban.objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class NonMovable implements CusObj {

    public NonMovable() {

    }

    @Override
    public File getFile() {
        return null;
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
