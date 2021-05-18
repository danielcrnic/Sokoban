package sokoban.objects;

import java.awt.*;

public interface CusObj {

    public int getTextureNumber();
    public Color modifyColor();

    public boolean isMovable();
    public boolean isSteppable();

}
