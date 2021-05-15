package sokoban.objects;

import java.awt.image.BufferedImage;

public class Wall implements CusObj {

    private BufferedImage bufferedImage;

    public Wall(BufferedImage bufferedImage) {
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
        return false;
    }
}
