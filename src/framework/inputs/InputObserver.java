package framework.inputs;

public interface InputObserver {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;
    public static final int BACK = 5;


    void keyPressed(int button);
}
