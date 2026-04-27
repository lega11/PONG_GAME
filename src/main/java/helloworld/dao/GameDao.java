package helloworld.dao;

import helloworld.model.GameModel;

/**
 * DAO interface for saving and loading game data from the database.
 */
public interface GameDao {

    /**
     * Saves the current game to the database.
     *
     * @param model game model to save
     * @return true if successful
     */
    boolean saveGame(GameModel model);

    /**
     * Loads the most recent game from the database.
     *
     * @return loaded game model or null if none was found
     */
    GameModel loadLatestGame();
}