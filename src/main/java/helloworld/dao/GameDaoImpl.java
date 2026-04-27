package helloworld.dao;

import helloworld.builder.GameBuilder;
import helloworld.db.DatabaseConnection;
import helloworld.model.GameModel;
import helloworld.model.GameSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DAO implementation for database operations related to the game.
 */
public class GameDaoImpl implements GameDao {

    /**
     * Saves the game state into the database table.
     *
     * @param model game model to save
     * @return true if save was successful
     */
    @Override
    public boolean saveGame(GameModel model) {
        String sql = "INSERT INTO game " +
                "(Game_Name, Player_1_Name, Player_2_Name, Player_1_Score, Player_2_Score, Score_Limit) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DatabaseConnection.getConnection();

            if (connection == null) {
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, "Pong Game");
            statement.setString(2, model.getPlayer1().getName());
            statement.setString(3, model.getPlayer2().getName());
            statement.setInt(4, model.getPlayer1().getScore());
            statement.setInt(5, model.getPlayer2().getScore());
            statement.setInt(6, model.getSettings().getWinningScore());

            statement.executeUpdate();
            statement.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Loads the latest saved game from the database.
     *
     * @return loaded game model or null if loading failed
     */
    @Override
    public GameModel loadLatestGame() {
        String sql = "SELECT * FROM game ORDER BY ID DESC LIMIT 1";

        try {
            Connection connection = DatabaseConnection.getConnection();

            if (connection == null) {
                return null;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                GameSettings settings = new GameSettings();
                settings.setWinningScore(resultSet.getInt("Score_Limit"));

                GameBuilder builder = new GameBuilder(settings);

                GameModel loadedGame = builder
                        .setPlayer1Name(resultSet.getString("Player_1_Name"))
                        .setPlayer2Name(resultSet.getString("Player_2_Name"))
                        .setPlayer1Score(resultSet.getInt("Player_1_Score"))
                        .setPlayer2Score(resultSet.getInt("Player_2_Score"))
                        .build();

                resultSet.close();
                statement.close();

                return loadedGame;
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}