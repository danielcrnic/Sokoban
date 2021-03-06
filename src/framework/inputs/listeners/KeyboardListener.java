package framework.inputs.listeners;

import framework.inputs.InputSubject;

import java.awt.*;
import java.awt.event.KeyEvent;

import static framework.inputs.InputObserver.*;

public class KeyboardListener implements KeyEventDispatcher {

    InputSubject subject;

    /**
     * Constructor for the KeyboardListener, this listener requires an InputSubject to be able to send keyboard presses
     * to the framework. This class is specifically designed for the GameFramework where it send input values defined by
     * by the GameFramework.
     *
     * @param subject The Subject that has the GameFramework as an observer
     */
    public KeyboardListener(InputSubject subject) {
        this.subject = subject;
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W, KeyEvent.VK_UP -> subject.notifyObserver(UP);
                case KeyEvent.VK_S, KeyEvent.VK_DOWN -> subject.notifyObserver(DOWN);
                case KeyEvent.VK_A, KeyEvent.VK_LEFT -> subject.notifyObserver(LEFT);
                case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> subject.notifyObserver(RIGHT);
                case KeyEvent.VK_ENTER -> subject.notifyObserver(ENTER);
                case KeyEvent.VK_ESCAPE -> subject.notifyObserver(BACK);
            }
        }

        return false;
    }

}
