package sokoban.objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Wall implements CusObj {

    public Wall() {

    }

    @Override
    public File getFile() {
        return new File("textures/wall.png");
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
