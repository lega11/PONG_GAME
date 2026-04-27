package helloworld.model;

import java.io.Serializable;

/**
 * Stores configurable settings for the game such as speed,
 * paddle size, winning score, and Total Mayhem values.
 */
public class GameSettings implements Serializable {

    private double ballSpeed = 4.0;
    private double paddleWidth = 20;
    private double paddleHeight = 120;
    private int winningScore = 5;

    private int speedIncreaseEveryBounces = 3;
    private double speedIncreaseAmount = 0.5;

    private boolean totalMayhem = false;
    private int mayhemPaddleChangeMs = 2000;
    private int mayhemBallChangeMs = 1500;

    public double getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public double getPaddleWidth() {
        return paddleWidth;
    }

    public void setPaddleWidth(double paddleWidth) {
        this.paddleWidth = paddleWidth;
    }

    public double getPaddleHeight() {
        return paddleHeight;
    }

    public void setPaddleHeight(double paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public int getWinningScore() {
        return winningScore;
    }

    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
    }

    public int getSpeedIncreaseEveryBounces() {
        return speedIncreaseEveryBounces;
    }

    public void setSpeedIncreaseEveryBounces(int speedIncreaseEveryBounces) {
        this.speedIncreaseEveryBounces = speedIncreaseEveryBounces;
    }

    public double getSpeedIncreaseAmount() {
        return speedIncreaseAmount;
    }

    public void setSpeedIncreaseAmount(double speedIncreaseAmount) {
        this.speedIncreaseAmount = speedIncreaseAmount;
    }

    public boolean isTotalMayhem() {
        return totalMayhem;
    }

    public void setTotalMayhem(boolean totalMayhem) {
        this.totalMayhem = totalMayhem;
    }

    public int getMayhemPaddleChangeMs() {
        return mayhemPaddleChangeMs;
    }

    public void setMayhemPaddleChangeMs(int mayhemPaddleChangeMs) {
        this.mayhemPaddleChangeMs = mayhemPaddleChangeMs;
    }

    public int getMayhemBallChangeMs() {
        return mayhemBallChangeMs;
    }

    public void setMayhemBallChangeMs(int mayhemBallChangeMs) {
        this.mayhemBallChangeMs = mayhemBallChangeMs;
    }
}