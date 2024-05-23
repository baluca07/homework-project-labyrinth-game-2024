package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {
    @Test
    void getRow() {
        assertEquals(-1, Direction.NORTH.getRowChange());
        assertEquals(0, Direction.EAST.getRowChange());
        assertEquals(0, Direction.WEST.getRowChange());
        assertEquals(1, Direction.SOUTH.getRowChange());
    }

    @Test
    void getCol() {
        assertEquals(0, Direction.NORTH.getColChange());
        assertEquals(1, Direction.EAST.getColChange());
        assertEquals(-1, Direction.WEST.getColChange());
        assertEquals(0, Direction.SOUTH.getColChange());
    }
}