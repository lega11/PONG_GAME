package helloworld.builder;

import helloworld.model.GameModel;
import helloworld.model.GameSettings;

/**
 * Builder class used to reconstruct a GameModel from database values.
 */
public class GameBuilder {

    private final GameModel model;

    /**
     * Creates a builder for constructing a game model.
     *
     * @param settings settings to use in the built model
     */
    public GameBuilder(GameSettings settings) {
        model = new GameModel(settings);
    }

    /**
     * Sets player 1 name in the built model.
     *
     * @param name player 1 name
     * @return current builder
     */
    public GameBuilder setPlayer1Name(String name) {
        model.getPlayer1().setName(name);
        return this;
    }

    /**
     * Sets player 2 name in the built model.
     *
     * @param name player 2 name
     * @return current builder
     */
    public GameBuilder setPlayer2Name(String name) {
        model.getPlayer2().setName(name);
        return this;
    }

    /**
     * Sets player 1 score in the built model.
     *
     * @param score player 1 score
     * @return current builder
     */
    public GameBuilder setPlayer1Score(int score) {
        model.getPlayer1().setScore(score);
        return this;
    }

    /**
     * Sets player 2 score in the built model.
     *
     * @param score player 2 score
     * @return current builder
     */
    public GameBuilder setPlayer2Score(int score) {
        model.getPlayer2().setScore(score);
        return this;
    }

    /**
     * Builds and returns the final game model.
     * The ball is reset to the centre when loading from the database.
     *
     * @return completed game model
     */
    public GameModel build() {
        model.getBall().setX(model.getFieldW() / 2);
        model.getBall().setY(model.getFieldH() / 2);
        model.getBall().setVx(0);
        model.getBall().setVy(0);
        return model;
    }
}