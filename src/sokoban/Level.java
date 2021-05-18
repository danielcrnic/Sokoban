package sokoban;

import sokoban.objects.CusObj;

public class Level {

    private CusObj[][] layout;

    private CusObj player;
    private CusObj[] objects;

    public Level(CusObj[][] layout, CusObj player, CusObj[] objects) {
        this.layout = layout;
        this.player = player;
        this.objects = objects;
    }

    public CusObj[][] getLayout() {
        return layout;
    }

    public CusObj[] getObjects() {
        return objects;
    }

    public CusObj getPlayer() {
        return player;
    }

    public void goUp() {

    }

    public void goDown() {

    }

    public void goLeft() {

    }

    public void goRight() {

    }

}
