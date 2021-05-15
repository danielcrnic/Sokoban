package sokoban.objects;

import java.awt.image.BufferedImage;

public class Floor implements CusObj {

    private BufferedImage bufferedImage;

    public Floor(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public BufferedImage getTexture() {
        return bufferedImage;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public boolean isStepable() {
        return true;
    }
}
