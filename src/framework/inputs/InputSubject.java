package framework.inputs;

public class InputSubject {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;

    private InputObserver observer;

    public InputSubject(InputObserver observer) {
        this.observer = observer;
    }

    public void changeObserver(InputObserver observer) {
        this.observer = observer;
    }

    public void notifyObserver(int keyPressed) {
        if (observer != null) {
            observer.keyPressed(keyPressed);
        }
    }

}