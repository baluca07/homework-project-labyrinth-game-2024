package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {
    @Test
    void getRow() {
        assertEquals(-1, Direction.NORTH.getRow());
        assertEquals(0, Direction.EAST.getRow());
        assertEquals(0, Direction.WEST.getRow());
        assertEquals(1, Direction.SOUTH.getRow());
    }

    @Test
    void getCol() {
        assertEquals(0, Direction.NORTH.getCol());
        assertEquals(1, Direction.EAST.getCol());
        assertEquals(-1, Direction.WEST.getCol());
        assertEquals(0, Direction.SOUTH.getCol());
    }
}