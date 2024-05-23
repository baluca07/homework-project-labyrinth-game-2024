package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    Player player = new Player(Position.of(0, 0));

    @Test
    void testToString() {
        assertEquals("Player is on the position (0,0).", player.toString());
    }

    @Test
    void getCurrentPosition() {
        assertEquals(Position.of(0, 0), player.getCurrentPosition());
    }
}