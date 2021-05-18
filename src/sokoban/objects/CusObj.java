package sokoban.objects;

import java.awt.*;
import java.io.File;

public interface CusObj {

    public File getFile();
    public Color modifyColor();

    public boolean isMovable();
    public boolean isSteppable();

}
