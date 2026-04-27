package helloworld.controller;

import helloworld.model.GameModel;

/**
 * Detects when a goal has been scored.
 */
public class GoalService {

    /**
     * Checks if the ball has passed the left or right edge.
     *
     * @param model game model
     * @return 0 if no goal, 1 if player 1 scored, 2 if player 2 scored
     */
    public int checkGoal(GameModel model) {
        double x = model.getBall().getX();

        if (x < 0) {
            return 2;
        }

        if (x > model.getFieldW()) {
            return 1;
        }

        return 0;
    }
}