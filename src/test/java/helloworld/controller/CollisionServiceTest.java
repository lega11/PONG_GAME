package helloworld.controller;

import helloworld.model.Ball;
import helloworld.model.Paddle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for collision detection.
 */
public class CollisionServiceTest {

    /**
     * Tests that a ball overlapping a paddle is detected as a hit.
     */
    @Test
    public void ballHitsPaddle() {
        CollisionService service = new CollisionService();

        Ball ball = new Ball(30, 100, 10, 4, 4);
        Paddle paddle = new Paddle(20, 80, 20, 60);

        assertTrue(service.hits(ball, paddle));
    }

    /**
     * Tests that a ball far away from a paddle is not detected as a hit.
     */
    @Test
    public void ballMissesPaddle() {
        CollisionService service = new CollisionService();

        Ball ball = new Ball(300, 300, 10, 4, 4);
        Paddle paddle = new Paddle(20, 80, 20, 60);

        assertFalse(service.hits(ball, paddle));
    }
}