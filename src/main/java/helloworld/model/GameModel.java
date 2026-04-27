package helloworld.model;

import java.io.Serializable;
import java.util.Random;

/**
 * Main game logic class.
 * Stores the complete game state including players, paddles,
 * ball, settings, messages, and effects.
 */
public class GameModel implements Serializable {

    private final GameSettings settings;

    private final Player player1 = new Player("Player 1");
    private final Player player2 = new Player("Player 2");

    private final Paddle leftPaddle;
    private final Paddle rightPaddle;
    private final Ball ball;

    private double fieldW = 600;
    private double fieldH = 640;

    private String message = "";
    private long messageUntilMs = 0;

    private final Random rand = new Random();

    private long hitUntilMs = 0;
    private double hitX = 0;
    private double hitY = 0;

    private int confettiSide = 0;
    private long confettiUntilMs = 0;

    private boolean gameStarted = false;
    private boolean paused = false;
    private boolean gameOver = false;
    private String winnerName = "";

    private int bounceCount = 0;

    /**
     * Creates the main game model and starting game objects.
     *
     * @param settings game settings object
     */
    public GameModel(GameSettings settings) {
        this.settings = settings;

        leftPaddle = new Paddle(20, 250, settings.getPaddleWidth(), settings.getPaddleHeight());
        rightPaddle = new Paddle(560, 250, settings.getPaddleWidth(), settings.getPaddleHeight());

        ball = new Ball(fieldW / 2, fieldH / 2, 15, 0, 0);
    }

    public GameSettings getSettings() {
        return settings;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Paddle getLeftPaddle() {
        return leftPaddle;
    }

    public Paddle getRightPaddle() {
        return rightPaddle;
    }

    public Ball getBall() {
        return ball;
    }

    public double getFieldW() {
        return fieldW;
    }

    public double getFieldH() {
        return fieldH;
    }

    /**
     * Updates the playfield size when the window is resized.
     *
     * @param w new width
     * @param h new height
     */
    public void setFieldSize(double w, double h) {
        fieldW = w;
        fieldH = h;
        rightPaddle.setX(w - 20 - rightPaddle.getWidth());
    }

    /**
     * Applies the current paddle size settings to both paddles.
     */
    public void applyPaddleSettings() {
        leftPaddle.setWidth(settings.getPaddleWidth());
        leftPaddle.setHeight(settings.getPaddleHeight());

        rightPaddle.setWidth(settings.getPaddleWidth());
        rightPaddle.setHeight(settings.getPaddleHeight());
        rightPaddle.setX(fieldW - 20 - rightPaddle.getWidth());
    }

    /**
     * Resets the ball to the centre and launches it in a random direction.
     */
    public void resetBallToCenterRandom() {
        ball.setX(fieldW / 2);
        ball.setY(fieldH / 2);

        double speed = settings.getBallSpeed();
        int dirX = rand.nextBoolean() ? 1 : -1;
        int dirY = rand.nextBoolean() ? 1 : -1;

        ball.setVx(dirX * speed);
        ball.setVy(dirY * speed);
    }

    /**
     * Displays a temporary message on the screen.
     *
     * @param msg message text
     * @param ms duration in milliseconds
     */
    public void showMessage(String msg, int ms) {
        message = msg;
        messageUntilMs = System.currentTimeMillis() + ms;
    }

    /**
     * Returns the current visible message if it has not expired yet.
     *
     * @return message text or empty string
     */
    public String getMessage() {
        if (System.currentTimeMillis() <= messageUntilMs) {
            return message;
        }
        return "";
    }

    /**
     * Starts a small hit effect at the given position.
     *
     * @param x x position of the effect
     * @param y y position of the effect
     */
    public void triggerHitEffect(double x, double y) {
        hitX = x;
        hitY = y;
        hitUntilMs = System.currentTimeMillis() + 120;
    }

    /**
     * Checks if the hit effect is still active.
     *
     * @return true if the effect should still be shown
     */
    public boolean isHitActive() {
        return System.currentTimeMillis() <= hitUntilMs;
    }

    public double getHitX() {
        return hitX;
    }

    public double getHitY() {
        return hitY;
    }

    /**
     * Starts a confetti effect on the scoring side.
     *
     * @param side 1 for left side, 2 for right side
     */
    public void triggerConfetti(int side) {
        confettiSide = side;
        confettiUntilMs = System.currentTimeMillis() + 1500;
    }

    /**
     * Checks if the confetti effect is still active.
     *
     * @return true if confetti should still be shown
     */
    public boolean isConfettiActive() {
        return System.currentTimeMillis() <= confettiUntilMs;
    }

    public int getConfettiSide() {
        return confettiSide;
    }

    /**
     * Checks whether the game has started.
     *
     * @return true if the player pressed ENTER to start
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Starts the game when ENTER is pressed.
     * Clears the start message and launches the ball.
     */
    public void startGame() {
        gameStarted = true;
        paused = false;
        gameOver = false;
        showMessage("", 0);
        resetBallToCenterRandom();
    }

    /**
     * Checks whether the game is paused.
     *
     * @return true if paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Updates the paused state.
     *
     * @param paused new paused state
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Checks whether the game is over.
     *
     * @return true if game over
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns the winner name if the game is over.
     *
     * @return winner name
     */
    public String getWinnerName() {
        return winnerName;
    }

    /**
     * Checks whether a player has reached the winning score.
     * If so, the game ends and the winner message is shown.
     */
    public void checkWinner() {
        if (player1.getScore() >= settings.getWinningScore()) {
            gameOver = true;
            winnerName = player1.getName();
            showMessage(winnerName + " WINS!", 999999999);
        }

        if (player2.getScore() >= settings.getWinningScore()) {
            gameOver = true;
            winnerName = player2.getName();
            showMessage(winnerName + " WINS!", 999999999);
        }
    }

    /**
     * Resets the full game state so a new match can start.
     */
    public void restartGame() {
        player1.setScore(0);
        player2.setScore(0);

        gameStarted = false;
        paused = false;
        gameOver = false;
        winnerName = "";
        bounceCount = 0;

        ball.setX(fieldW / 2);
        ball.setY(fieldH / 2);
        ball.setVx(0);
        ball.setVy(0);

        showMessage("Press ENTER to start", 999999999);
    }

    /**
     * Increases the bounce counter and speeds up the ball
     * after a set number of bounces.
     */
    public void registerBounce() {
        bounceCount++;

        if (bounceCount % settings.getSpeedIncreaseEveryBounces() == 0) {
            increaseBallSpeed();
        }
    }

    /**
     * Increases the ball speed in both directions.
     */
    public void increaseBallSpeed() {
        double increase = settings.getSpeedIncreaseAmount();

        if (ball.getVx() > 0) {
            ball.setVx(ball.getVx() + increase);
        } else if (ball.getVx() < 0) {
            ball.setVx(ball.getVx() - increase);
        }

        if (ball.getVy() > 0) {
            ball.setVy(ball.getVy() + increase);
        } else if (ball.getVy() < 0) {
            ball.setVy(ball.getVy() - increase);
        }
    }

    /**
     * Randomly changes the paddle sizes for Total Mayhem mode.
     */
    public void randomizePaddles() {
        double newW = 10 + rand.nextInt(31);
        double newH = 60 + rand.nextInt(141);

        settings.setPaddleWidth(newW);
        settings.setPaddleHeight(newH);

        applyPaddleSettings();
    }

    /**
     * Randomly changes the current ball direction for Total Mayhem mode.
     */
    public void randomizeBallDirection() {
        double vx = ball.getVx();
        double vy = ball.getVy();

        if (vx == 0 && vy == 0) {
            return;
        }

        double speedX = Math.abs(vx);
        double speedY = Math.abs(vy);

        int dirX = rand.nextBoolean() ? 1 : -1;
        int dirY = rand.nextBoolean() ? 1 : -1;

        ball.setVx(dirX * speedX);
        ball.setVy(dirY * speedY);
    }

    /**
     * Copies state from another game model into this one.
     * This is used when loading a saved game without replacing
     * the current object already connected to the UI.
     *
     * @param other loaded game model
     */
    public void copyFrom(GameModel other) {
        settings.setBallSpeed(other.getSettings().getBallSpeed());
        settings.setPaddleWidth(other.getSettings().getPaddleWidth());
        settings.setPaddleHeight(other.getSettings().getPaddleHeight());
        settings.setWinningScore(other.getSettings().getWinningScore());
        settings.setSpeedIncreaseEveryBounces(other.getSettings().getSpeedIncreaseEveryBounces());
        settings.setSpeedIncreaseAmount(other.getSettings().getSpeedIncreaseAmount());
        settings.setTotalMayhem(other.getSettings().isTotalMayhem());
        settings.setMayhemPaddleChangeMs(other.getSettings().getMayhemPaddleChangeMs());
        settings.setMayhemBallChangeMs(other.getSettings().getMayhemBallChangeMs());

        player1.setName(other.getPlayer1().getName());
        player2.setName(other.getPlayer2().getName());
        player1.setScore(other.getPlayer1().getScore());
        player2.setScore(other.getPlayer2().getScore());

        leftPaddle.setX(other.getLeftPaddle().getX());
        leftPaddle.setY(other.getLeftPaddle().getY());
        leftPaddle.setWidth(other.getLeftPaddle().getWidth());
        leftPaddle.setHeight(other.getLeftPaddle().getHeight());

        rightPaddle.setX(other.getRightPaddle().getX());
        rightPaddle.setY(other.getRightPaddle().getY());
        rightPaddle.setWidth(other.getRightPaddle().getWidth());
        rightPaddle.setHeight(other.getRightPaddle().getHeight());

        ball.setX(other.getBall().getX());
        ball.setY(other.getBall().getY());
        ball.setRadius(other.getBall().getRadius());
        ball.setVx(other.getBall().getVx());
        ball.setVy(other.getBall().getVy());

        fieldW = other.getFieldW();
        fieldH = other.getFieldH();

        message = other.message;
        messageUntilMs = other.messageUntilMs;

        hitUntilMs = other.hitUntilMs;
        hitX = other.hitX;
        hitY = other.hitY;

        confettiSide = other.confettiSide;
        confettiUntilMs = other.confettiUntilMs;

        gameStarted = other.gameStarted;
        paused = other.paused;
        gameOver = other.gameOver;
        winnerName = other.winnerName;
        bounceCount = other.bounceCount;
    }
}