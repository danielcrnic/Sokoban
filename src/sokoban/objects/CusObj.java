package sokoban.objects;

import java.awt.image.BufferedImage;

public interface CusObj {

    public BufferedImage getTexture();

    public boolean isMovable();
    public boolean isStepable();

}
