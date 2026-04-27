package helloworld.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Stores the current key states used for paddle movement.
 */
public class PaddleKeyHandler implements EventHandler<KeyEvent> {

    private boolean p1Up;
    private boolean p1Down;
    private boolean p2Up;
    private boolean p2Down;

    /**
     * Updates movement flags when a key is pressed.
     *
     * @param event key press event
     */
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case W -> p1Up = true;
            case S -> p1Down = true;
            case UP -> p2Up = true;
            case DOWN -> p2Down = true;
        }
    }

    /**
     * Updates movement flags when a key is released.
     *
     * @param event key release event
     */
    public void handleRelease(KeyEvent event) {
        switch (event.getCode()) {
            case W -> p1Up = false;
            case S -> p1Down = false;
            case UP -> p2Up = false;
            case DOWN -> p2Down = false;
        }
    }

    public boolean isP1Up() {
        return p1Up;
    }

    public boolean isP1Down() {
        return p1Down;
    }

    public boolean isP2Up() {
        return p2Up;
    }

    public boolean isP2Down() {
        return p2Down;
    }
}