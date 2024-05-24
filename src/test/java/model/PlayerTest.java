package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    Player player = new Player(Position.of(1, 1));

    @Test
    void testToString() {
        assertEquals("Player is on the position (1,1).", player.toString());
    }

    @Test
    void getCurrentPosition() {
        assertEquals(Position.of(1, 1), player.getCurrentPosition());
    }
}