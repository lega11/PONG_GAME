package helloworld.service;

import helloworld.model.GameModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Handles saving and loading game state using file serialization.
 * Implemented as a Singleton.
 */
public class SerializationService {

    private static SerializationService instance;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private SerializationService() {
    }

    /**
     * Returns the single shared instance of the service.
     *
     * @return singleton instance
     */
    public static SerializationService getInstance() {
        if (instance == null) {
            instance = new SerializationService();
        }
        return instance;
    }

    /**
     * Saves the current game state to a file.
     *
     * @param model game model to save
     * @return true if save was successful
     */
    public boolean saveGame(GameModel model) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("save.dat"))) {

            out.writeObject(model);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Loads a saved game from a file.
     *
     * @return loaded game model or null if loading failed
     */
    public GameModel loadGame() {
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("save.dat"))) {

            return (GameModel) in.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}