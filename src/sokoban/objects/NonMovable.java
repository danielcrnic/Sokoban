package sokoban.objects;

import java.awt.image.BufferedImage;

public class NonMovable implements CusObj {

    private final BufferedImage bufferedImage;  // FIXME: If we have animations, should this still be final?

    public NonMovable(BufferedImage bufferedImage) {
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
