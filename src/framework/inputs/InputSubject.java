package framework.inputs;

public class InputSubject {


    private final InputObserver observer;

    /**
     * Constructor for an InputSubject, this Subject takes care to send input presses to an specific Observer. The
     * observer is final and cannot be changed.
     *
     * Methods that want to notify the observer uses this object and calls the method notifyObserver with the relevant
     * input value (which should be defined by the Observer)
     *
     * @param observer The observer which should receives commands from other methods
     */
    public InputSubject(InputObserver observer) {
        this.observer = observer;
    }

    /**
     * Method that is used to notify the observer that a new input has been sent.
     *
     * @param keyPressed Input value that should be defined by the observer
     */
    public void notifyObserver(int keyPressed) {
        if (observer != null) {
            observer.keyPressed(keyPressed);
        }
    }

}