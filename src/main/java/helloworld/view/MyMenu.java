package helloworld.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Creates the menu bar for the Pong game.
 * Contains game controls and configurable settings.
 */
public class MyMenu {

    private final MenuBar menuBar = new MenuBar();

    private final Menu menuGame = new Menu("Game");
    private final Menu menuSettings = new Menu("Settings");

    private final MenuItem miPause = new MenuItem("Pause / Resume");
    private final MenuItem miRestart = new MenuItem("Restart Game");
    private final MenuItem miSaveFile = new MenuItem("Save Game To File");
    private final MenuItem miLoadFile = new MenuItem("Load Game From File");
    private final MenuItem miSaveDb = new MenuItem("Save Game To Database");
    private final MenuItem miLoadDb = new MenuItem("Load Latest Game From Database");
    private final MenuItem miExit = new MenuItem("Exit");

    private final MenuItem miSetNames = new MenuItem("Set player names...");
    private final MenuItem miSetBallSpeed = new MenuItem("Set ball speed...");
    private final MenuItem miSetPaddle = new MenuItem("Set racket size/thickness...");
    private final MenuItem miSetWinningScore = new MenuItem("Set winning score...");
    private final MenuItem miSetSpeedIncrease = new MenuItem("Set speed increase rule...");
    private final MenuItem miShowRules = new MenuItem("Show current rules");
    private final MenuItem miTotalMayhem = new MenuItem("Toggle Total Mayhem");

    /**
     * Builds the menu bar and adds all menu items.
     */
    public MyMenu() {
        menuGame.getItems().addAll(
                miPause,
                miRestart,
                miSaveFile,
                miLoadFile,
                miSaveDb,
                miLoadDb,
                miExit
        );

        menuSettings.getItems().addAll(
                miSetNames,
                miSetBallSpeed,
                miSetPaddle,
                miSetWinningScore,
                miSetSpeedIncrease,
                miShowRules,
                miTotalMayhem
        );

        menuBar.getMenus().addAll(menuGame, menuSettings);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public MenuItem getMiPause() {
        return miPause;
    }

    public MenuItem getMiRestart() {
        return miRestart;
    }

    public MenuItem getMiSaveFile() {
        return miSaveFile;
    }

    public MenuItem getMiLoadFile() {
        return miLoadFile;
    }

    public MenuItem getMiSaveDb() {
        return miSaveDb;
    }

    public MenuItem getMiLoadDb() {
        return miLoadDb;
    }

    public MenuItem getMiExit() {
        return miExit;
    }

    public MenuItem getMiSetNames() {
        return miSetNames;
    }

    public MenuItem getMiSetBallSpeed() {
        return miSetBallSpeed;
    }

    public MenuItem getMiSetPaddle() {
        return miSetPaddle;
    }

    public MenuItem getMiSetWinningScore() {
        return miSetWinningScore;
    }

    public MenuItem getMiSetSpeedIncrease() {
        return miSetSpeedIncrease;
    }

    public MenuItem getMiShowRules() {
        return miShowRules;
    }

    public MenuItem getMiTotalMayhem() {
        return miTotalMayhem;
    }
}