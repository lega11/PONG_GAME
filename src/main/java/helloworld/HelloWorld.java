package helloworld;

import helloworld.controller.BallThread;
import helloworld.controller.PaddleKeyHandler;
import helloworld.controller.PaddleMover;
import helloworld.dao.GameDao;
import helloworld.dao.GameDaoImpl;
import helloworld.model.GameModel;
import helloworld.model.GameSettings;
import helloworld.service.SerializationService;
import helloworld.view.LabCanvas;
import helloworld.view.MyMenu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main application class for the Pong game.
 * This class creates the window, menu, canvas, and connects the model,
 * view, and controller parts together.
 */
public class HelloWorld extends Application {

    private BallThread ballThread;

    /**
     * Starts the JavaFX application and builds the main game window.
     *
     * @param stage the main stage for the application
     */
    @Override
    public void start(Stage stage) {
        double windowWidth = 600;
        double windowHeight = 640;

        GameSettings settings = new GameSettings();
        GameModel model = new GameModel(settings);
        GameDao gameDao = new GameDaoImpl();

        model.showMessage("Press ENTER to start", 999999999);

        BorderPane root = new BorderPane();
        MyMenu menu = new MyMenu();
        root.setTop(menu.getMenuBar());

        LabCanvas canvas = new LabCanvas(model);
        StackPane canvasHolder = new StackPane();
        canvasHolder.getChildren().add(canvas);

        canvasHolder.setMinSize(0, 0);
        root.setMinSize(0, 0);

        canvas.widthProperty().bind(canvasHolder.widthProperty());
        canvas.heightProperty().bind(canvasHolder.heightProperty());

        root.setCenter(canvasHolder);

        stage.setMinWidth(400);
        stage.setMinHeight(400);

        Scene scene = new Scene(root, windowWidth, windowHeight);
        stage.setScene(scene);
        stage.setTitle("Pong - Final Version");
        stage.show();

        root.requestFocus();

        PaddleKeyHandler keyHandler = new PaddleKeyHandler();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == javafx.scene.input.KeyCode.ENTER) {
                if (!model.isGameStarted()) {
                    model.startGame();
                    canvas.redraw();
                }
            }

            if (e.getCode() == javafx.scene.input.KeyCode.ESCAPE) {
                model.setPaused(!model.isPaused());

                if (model.isPaused()) {
                    model.showMessage("PAUSED", 999999999);
                } else if (!model.isGameOver()) {
                    model.showMessage("", 0);
                }

                canvas.redraw();
            }

            keyHandler.handle(e);
        });

        scene.setOnKeyReleased(keyHandler::handleRelease);

        PaddleMover paddleMover = new PaddleMover(model, keyHandler);
        paddleMover.start();

        ballThread = new BallThread(model, canvas);
        ballThread.start();

        menu.getMiExit().setOnAction(e -> {
            stopThreads();
            Platform.exit();
        });

        menu.getMiPause().setOnAction(e -> {
            model.setPaused(!model.isPaused());

            if (model.isPaused()) {
                model.showMessage("PAUSED", 999999999);
            } else if (!model.isGameOver()) {
                model.showMessage("", 0);
            }

            canvas.redraw();
        });

        menu.getMiRestart().setOnAction(e -> {
            model.restartGame();
            canvas.redraw();
        });

        menu.getMiSaveFile().setOnAction(e -> {
            boolean ok = SerializationService.getInstance().saveGame(model);
            showPopup(ok ? "Game saved to file." : "Could not save game.");
        });

        menu.getMiLoadFile().setOnAction(e -> {
            GameModel loaded = SerializationService.getInstance().loadGame();

            if (loaded != null) {
                model.copyFrom(loaded);
                canvas.redraw();
                showPopup("Game loaded from file.");
            } else {
                showPopup("Could not load game.");
            }
        });

        menu.getMiSaveDb().setOnAction(e -> {
            boolean ok = gameDao.saveGame(model);
            showPopup(ok ? "Game saved to database." : "Could not save to database.");
        });

        menu.getMiLoadDb().setOnAction(e -> {
            GameModel loaded = gameDao.loadLatestGame();

            if (loaded != null) {
                model.copyFrom(loaded);
                canvas.redraw();
                showPopup("Latest game loaded from database.");
            } else {
                showPopup("Could not load from database.");
            }
        });

        menu.getMiTotalMayhem().setOnAction(e -> {
            boolean newValue = !settings.isTotalMayhem();
            settings.setTotalMayhem(newValue);

            if (newValue) {
                model.showMessage("TOTAL MAYHEM ON!", 1500);
                model.randomizePaddles();

                if (model.isGameStarted()) {
                    model.randomizeBallDirection();
                }
            } else {
                model.showMessage("Total Mayhem OFF", 1500);
            }

            canvas.redraw();
        });

        menu.getMiSetNames().setOnAction(e -> {
            TextInputDialog dialog1 = new TextInputDialog(model.getPlayer1().getName());
            dialog1.setTitle("Player 1");
            dialog1.setHeaderText(null);
            dialog1.setContentText("Enter Player 1 name:");
            dialog1.showAndWait().ifPresent(name -> {
                if (!name.trim().isEmpty()) {
                    model.getPlayer1().setName(name.trim());
                }
            });

            TextInputDialog dialog2 = new TextInputDialog(model.getPlayer2().getName());
            dialog2.setTitle("Player 2");
            dialog2.setHeaderText(null);
            dialog2.setContentText("Enter Player 2 name:");
            dialog2.showAndWait().ifPresent(name -> {
                if (!name.trim().isEmpty()) {
                    model.getPlayer2().setName(name.trim());
                }
            });

            canvas.redraw();
        });

        menu.getMiSetBallSpeed().setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog(String.valueOf(settings.getBallSpeed()));
            dialog.setTitle("Ball Speed");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter ball speed:");
            dialog.showAndWait().ifPresent(value -> {
                try {
                    double speed = Double.parseDouble(value);
                    if (speed > 0) {
                        settings.setBallSpeed(speed);
                    }
                } catch (NumberFormatException ignored) {
                }
            });
        });

        menu.getMiSetPaddle().setOnAction(e -> {
            TextInputDialog widthDialog = new TextInputDialog(String.valueOf(settings.getPaddleWidth()));
            widthDialog.setTitle("Racket Thickness");
            widthDialog.setHeaderText(null);
            widthDialog.setContentText("Enter racket thickness:");
            widthDialog.showAndWait().ifPresent(value -> {
                try {
                    double paddleWidth = Double.parseDouble(value);
                    if (paddleWidth > 0) {
                        settings.setPaddleWidth(paddleWidth);
                    }
                } catch (NumberFormatException ignored) {
                }
            });

            TextInputDialog heightDialog = new TextInputDialog(String.valueOf(settings.getPaddleHeight()));
            heightDialog.setTitle("Racket Size");
            heightDialog.setHeaderText(null);
            heightDialog.setContentText("Enter racket height:");
            heightDialog.showAndWait().ifPresent(value -> {
                try {
                    double paddleHeight = Double.parseDouble(value);
                    if (paddleHeight > 0) {
                        settings.setPaddleHeight(paddleHeight);
                    }
                } catch (NumberFormatException ignored) {
                }
            });

            model.applyPaddleSettings();
            canvas.redraw();
        });

        menu.getMiSetWinningScore().setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog(String.valueOf(settings.getWinningScore()));
            dialog.setTitle("Winning Score");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter winning score:");
            dialog.showAndWait().ifPresent(value -> {
                try {
                    int winningScore = Integer.parseInt(value);
                    if (winningScore > 0) {
                        settings.setWinningScore(winningScore);
                    }
                } catch (NumberFormatException ignored) {
                }
            });
        });

        menu.getMiSetSpeedIncrease().setOnAction(e -> {
            TextInputDialog dialog1 = new TextInputDialog(String.valueOf(settings.getSpeedIncreaseEveryBounces()));
            dialog1.setTitle("Speed Increase Frequency");
            dialog1.setHeaderText(null);
            dialog1.setContentText("Increase speed after how many bounces?");
            dialog1.showAndWait().ifPresent(value -> {
                try {
                    int every = Integer.parseInt(value);
                    if (every > 0) {
                        settings.setSpeedIncreaseEveryBounces(every);
                    }
                } catch (NumberFormatException ignored) {
                }
            });

            TextInputDialog dialog2 = new TextInputDialog(String.valueOf(settings.getSpeedIncreaseAmount()));
            dialog2.setTitle("Speed Increase Amount");
            dialog2.setHeaderText(null);
            dialog2.setContentText("Increase speed by how much?");
            dialog2.showAndWait().ifPresent(value -> {
                try {
                    double amount = Double.parseDouble(value);
                    if (amount > 0) {
                        settings.setSpeedIncreaseAmount(amount);
                    }
                } catch (NumberFormatException ignored) {
                }
            });
        });

        menu.getMiShowRules().setOnAction(e -> {
            String rules =
                    "Player 1: " + model.getPlayer1().getName() + "\n" +
                            "Player 2: " + model.getPlayer2().getName() + "\n\n" +
                            "Ball speed: " + settings.getBallSpeed() + "\n" +
                            "Racket width: " + settings.getPaddleWidth() + "\n" +
                            "Racket height: " + settings.getPaddleHeight() + "\n\n" +
                            "Winning score: " + settings.getWinningScore() + "\n" +
                            "Speed increases every " + settings.getSpeedIncreaseEveryBounces() +
                            " bounces by " + settings.getSpeedIncreaseAmount() + "\n\n" +
                            "Total Mayhem: " + (settings.isTotalMayhem() ? "ON" : "OFF");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Current Game Rules");
            alert.setHeaderText("Game Settings");
            alert.setContentText(rules);
            alert.showAndWait();
        });
    }

    /**
     * Shows a simple popup message.
     *
     * @param text the message to display
     */
    private void showPopup(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pong");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Stops the ball thread safely before exit.
     */
    private void stopThreads() {
        if (ballThread != null) {
            ballThread.stopRunning();
        }
    }

    /**
     * Called automatically when the application closes.
     */
    @Override
    public void stop() {
        stopThreads();
    }

    public static void main(String[] args) {
        launch(args);
    }
}