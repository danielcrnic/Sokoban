package framework.inputs;

public class InputSubject {


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