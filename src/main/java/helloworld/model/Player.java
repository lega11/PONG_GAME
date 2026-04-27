package helloworld.model;

import java.io.Serializable;

/**
 * Represents a player in the Pong game.
 * Stores the player's name and score.
 */
public class Player implements Serializable {

    private String name;
    private int score;

    /**
     * Creates a new player with the given name.
     * The score starts at 0.
     *
     * @param name the player's name
     */
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Returns the player's name.
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the player's name.
     *
     * @param name new player name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the current player score.
     *
     * @return player score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score directly.
     *
     * @param score new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Increases the player's score by 1.
     */
    public void addPoint() {
        score++;
    }
}