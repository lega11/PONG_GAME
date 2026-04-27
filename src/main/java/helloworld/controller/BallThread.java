package helloworld.controller;

import helloworld.model.GameModel;
import helloworld.view.LabCanvas;
import javafx.application.Platform;

/**
 * Thread responsible for moving the ball and updating the game.
 * Runs separately from paddle movement.
 */
public class BallThread extends Thread {

    private final GameModel model;
    private final LabCanvas canvas;

    private final CollisionService collisionService = new CollisionService();
    private final GoalService goalService = new GoalService();

    private volatile boolean running = true;

    private long nextMayhemPaddleChange = 0;
    private long nextMayhemBallChange = 0;

    /**
     * Creates the ball movement thread.
     *
     * @param model game model
     * @param canvas canvas used for redrawing
     */
    public BallThread(GameModel model, LabCanvas canvas) {
        this.model = model;
        this.canvas = canvas;
        setDaemon(true);
    }

    /**
     * Stops the thread safely.
     */
    public void stopRunning() {
        running = false;
    }

    /**
     * Pauses the ball while a goal animation plays,
     * then restarts the ball from the centre.
     */
    private void handleGoalPauseAndRestart() {
        model.getBall().setX(model.getFieldW() / 2);
        model.getBall().setY(model.getFieldH() / 2);
        model.getBall().setVx(0);
        model.getBall().setVy(0);

        while (running && model.isConfettiActive()) {
            Platform.runLater(canvas::redraw);
            sleepShort(50);
        }

        model.resetBallToCenterRandom();
    }

    /**
     * Main loop of the ball thread.
     * Handles movement, goals, pause state, and game over state.
     */
    @Override
    public void run() {
        while (running) {

            if (!model.isGameStarted()) {
                Platform.runLater(canvas::redraw);
                sleepShort(50);
                continue;
            }

            if (model.isPaused()) {
                Platform.runLater(canvas::redraw);
                sleepShort(50);
                continue;
            }

            if (model.isGameOver()) {
                Platform.runLater(canvas::redraw);
                sleepShort(50);
                continue;
            }

            handleTotalMayhem();
            moveBall();

            collisionService.bounceWalls(model);
            collisionService.bouncePaddles(model);

            if (handleGoal()) {
                continue;
            }

            Platform.runLater(canvas::redraw);
            sleepShort(16);
        }
    }

    /**
     * Applies random Total Mayhem effects while the mode is enabled.
     */
    private void handleTotalMayhem() {
        if (model.getSettings().isTotalMayhem()) {
            long now = System.currentTimeMillis();

            if (nextMayhemPaddleChange == 0) {
                nextMayhemPaddleChange = now;
            }

            if (nextMayhemBallChange == 0) {
                nextMayhemBallChange = now;
            }

            if (now >= nextMayhemPaddleChange) {
                model.randomizePaddles();
                nextMayhemPaddleChange = now + model.getSettings().getMayhemPaddleChangeMs();
            }

            if (now >= nextMayhemBallChange) {
                model.randomizeBallDirection();
                nextMayhemBallChange = now + model.getSettings().getMayhemBallChangeMs();
            }
        } else {
            nextMayhemPaddleChange = 0;
            nextMayhemBallChange = 0;
        }
    }

    /**
     * Moves the ball using its current velocity.
     */
    private void moveBall() {
        model.getBall().setX(model.getBall().getX() + model.getBall().getVx());
        model.getBall().setY(model.getBall().getY() + model.getBall().getVy());
    }

    /**
     * Checks for goals and updates the game state when one happens.
     *
     * @return true if a goal happened, otherwise false
     */
    private boolean handleGoal() {
        int goal = goalService.checkGoal(model);

        if (goal == 1) {
            model.getPlayer1().addPoint();
            model.showMessage("GOAL " + model.getPlayer1().getName(), 2000);
            model.triggerConfetti(2);
            model.checkWinner();

            if (!model.isGameOver()) {
                handleGoalPauseAndRestart();
            }

            return true;
        }

        if (goal == 2) {
            model.getPlayer2().addPoint();
            model.showMessage("GOAL " + model.getPlayer2().getName(), 2000);
            model.triggerConfetti(1);
            model.checkWinner();

            if (!model.isGameOver()) {
                handleGoalPauseAndRestart();
            }

            return true;
        }

        return false;
    }

    /**
     * Small helper method used to pause the thread.
     *
     * @param ms number of milliseconds to sleep
     */
    private void sleepShort(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}