package helloworld.controller;

import helloworld.model.GameModel;
import helloworld.model.Paddle;
import javafx.animation.AnimationTimer;

/**
 * Moves the paddles while movement keys are pressed.
 */
public class PaddleMover extends AnimationTimer {

    private final GameModel model;
    private final PaddleKeyHandler keys;

    /**
     * Creates the paddle movement controller.
     *
     * @param model game model
     * @param keys keyboard state holder
     */
    public PaddleMover(GameModel model, PaddleKeyHandler keys) {
        this.model = model;
        this.keys = keys;
    }

    /**
     * Updates paddle positions every frame.
     *
     * @param now current animation time
     */
    @Override
    public void handle(long now) {
        if (model.isPaused() || model.isGameOver()) {
            return;
        }

        Paddle p1 = model.getLeftPaddle();
        Paddle p2 = model.getRightPaddle();

        double speed = 6;
        double h = model.getFieldH();

        if (keys.isP1Up()) p1.setY(p1.getY() - speed);
        if (keys.isP1Down()) p1.setY(p1.getY() + speed);

        if (keys.isP2Up()) p2.setY(p2.getY() - speed);
        if (keys.isP2Down()) p2.setY(p2.getY() + speed);

        clamp(p1, h);
        clamp(p2, h);
    }

    /**
     * Keeps a paddle inside the visible play area.
     *
     * @param paddle paddle to clamp
     * @param height playfield height
     */
    private void clamp(Paddle paddle, double height) {
        if (paddle.getY() < 0) {
            paddle.setY(0);
        }

        if (paddle.getY() + paddle.getHeight() > height) {
            paddle.setY(height - paddle.getHeight());
        }
    }
}