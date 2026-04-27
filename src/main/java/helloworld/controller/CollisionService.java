package helloworld.controller;

import helloworld.model.Ball;
import helloworld.model.GameModel;
import helloworld.model.Paddle;

/**
 * Handles collision detection for the ball.
 * Includes wall and paddle collisions.
 */
public class CollisionService {

    /**
     * Makes the ball bounce off the top and bottom walls.
     *
     * @param model game model
     */
    public void bounceWalls(GameModel model) {
        Ball ball = model.getBall();
        double radius = ball.getRadius();

        if (ball.getY() - radius <= 0 || ball.getY() + radius >= model.getFieldH()) {
            ball.setVy(-ball.getVy());
            model.triggerHitEffect(ball.getX(), ball.getY());
            model.registerBounce();
        }
    }

    /**
     * Makes the ball bounce when it collides with either paddle.
     *
     * @param model game model
     */
    public void bouncePaddles(GameModel model) {
        Ball ball = model.getBall();
        Paddle leftPaddle = model.getLeftPaddle();
        Paddle rightPaddle = model.getRightPaddle();

        if (hits(ball, leftPaddle) && ball.getVx() < 0) {
            ball.setVx(-ball.getVx());
            model.triggerHitEffect(ball.getX(), ball.getY());
            model.registerBounce();
        }

        if (hits(ball, rightPaddle) && ball.getVx() > 0) {
            ball.setVx(-ball.getVx());
            model.triggerHitEffect(ball.getX(), ball.getY());
            model.registerBounce();
        }
    }

    /**
     * Checks if the ball overlaps with a paddle.
     *
     * @param ball the ball
     * @param paddle the paddle
     * @return true if a collision happens
     */
    public boolean hits(Ball ball, Paddle paddle) {
        double ballX = ball.getX();
        double ballY = ball.getY();
        double radius = ball.getRadius();

        return (ballX + radius >= paddle.getX() &&
                ballX - radius <= paddle.getX() + paddle.getWidth() &&
                ballY + radius >= paddle.getY() &&
                ballY - radius <= paddle.getY() + paddle.getHeight());
    }
}