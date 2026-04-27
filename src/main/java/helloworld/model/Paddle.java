package helloworld.model;

import java.io.Serializable;

/**
 * Represents a paddle in the game.
 * Stores its position and size.
 */
public class Paddle implements Serializable {

    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * Creates a new paddle.
     *
     * @param x paddle x position
     * @param y paddle y position
     * @param width paddle width
     * @param height paddle height
     */
    public Paddle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}