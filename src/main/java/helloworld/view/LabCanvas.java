package helloworld.view;

import helloworld.model.GameModel;
import helloworld.model.Paddle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

/**
 * Canvas used to draw the visual game state.
 */
public class LabCanvas extends Canvas {

    private final GameModel model;
    private final Random rand = new Random();

    /**
     * Creates the drawing canvas.
     *
     * @param model game model used for drawing
     */
    public LabCanvas(GameModel model) {
        this.model = model;

        widthProperty().addListener(e -> redraw());
        heightProperty().addListener(e -> redraw());
    }

    /**
     * Redraws the current game state on the canvas.
     */
    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();

        double w = getWidth();
        double h = getHeight();

        model.setFieldSize(w, h);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, w, h);

        double margin = 20;
        model.getLeftPaddle().setX(margin);
        model.getRightPaddle().setX(w - margin - model.getRightPaddle().getWidth());

        gc.setFont(new Font(20));
        gc.setFill(Color.WHITE);

        gc.fillText(model.getPlayer1().getName() + ": " + model.getPlayer1().getScore(), 20, 30);
        gc.fillText(model.getPlayer2().getName() + ": " + model.getPlayer2().getScore(), w - 200, 30);

        gc.setFill(Color.WHITE);
        drawPaddle(gc, model.getLeftPaddle());
        drawPaddle(gc, model.getRightPaddle());

        if (!model.isConfettiActive() && model.isGameStarted()) {
            double r = model.getBall().getRadius();
            gc.fillOval(
                    model.getBall().getX() - r,
                    model.getBall().getY() - r,
                    r * 2,
                    r * 2
            );
        }

        if (model.isHitActive()) {
            gc.setStroke(Color.WHITE);
            gc.strokeOval(model.getHitX() - 25, model.getHitY() - 25, 50, 50);
        }

        if (model.isConfettiActive()) {
            double confettiWidth = 60;
            double startX;

            if (model.getConfettiSide() == 1) {
                startX = 0;
            } else {
                startX = w - confettiWidth;
            }

            for (int i = 0; i < 80; i++) {
                double x = startX + rand.nextDouble() * confettiWidth;
                double y = rand.nextDouble() * h;

                int c = rand.nextInt(4);
                if (c == 0) gc.setFill(Color.RED);
                if (c == 1) gc.setFill(Color.YELLOW);
                if (c == 2) gc.setFill(Color.LIME);
                if (c == 3) gc.setFill(Color.CYAN);

                gc.fillRect(x, y, 6, 6);
            }
        }

        String msg = model.getMessage();
        if (!msg.isEmpty()) {
            gc.setFont(new Font(30));
            gc.setFill(Color.WHITE);
            gc.fillText(msg, w / 2 - 120, h / 2);
        }
    }

    /**
     * Draws one paddle on the canvas.
     *
     * @param gc graphics context
     * @param paddle paddle to draw
     */
    private void drawPaddle(GraphicsContext gc, Paddle paddle) {
        gc.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
    }
}