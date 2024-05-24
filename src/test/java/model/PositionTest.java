package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PositionTest {
    Position position = new Position(1, 1);

    @Test
    void of() {
        assertEquals(new Position(1, 1), Position.of(1, 1));
        assertNotEquals(new Position(1, 1), Position.of(2, 1));
    }

    @Test
    void testToString() {
        assertEquals("(1,1)", position.toString());
    }

    @Test
    void testEquals() {
        assertEquals(Position.of(1, 1), position);
        assertNotEquals(Position.of(2, 1), position);
    }

    @Test
    void getRow() {
        assertEquals(1, position.getRow());
        assertNotEquals(2, position.getRow());
    }

    @Test
    void getCol() {
        assertEquals(1, position.getCol());
        assertNotEquals(2, position.getCol());
    }

    @Test
    void setRow() {
        Position newPosition = Position.of(position.getRow(), position.getCol());
        newPosition.setRow(3);
        assertNotEquals(position.getRow(), newPosition.getRow());
        Position positionExpected = new Position(3, 2);
        assertEquals(positionExpected.getRow(), newPosition.getRow());
    }

    @Test
    void setCol() {
        Position newPosition = Position.of(position.getRow(), position.getCol());
        newPosition.setCol(3);
        assertNotEquals(position.getCol(), newPosition.getCol());
        Position positionExpected = new Position(2, 3);
        assertEquals(positionExpected.getCol(), newPosition.getCol());
    }
}