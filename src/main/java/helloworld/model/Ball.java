package helloworld.model;

import java.io.Serializable;

/**
 * Represents the ball in the game.
 * Stores position, radius, and movement speed.
 */
public class Ball implements Serializable {

    private double x;
    private double y;
    private double radius;
    private double vx;
    private double vy;

    /**
     * Creates a new ball object.
     *
     * @param x starting x position
     * @param y starting y position
     * @param radius radius of the ball
     * @param vx horizontal speed
     * @param vy vertical speed
     */
    public Ball(double x, double y, double radius, double vx, double vy) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }
}