package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PositionTest {
    Position position = new Position(0, 0);

    @Test
    void of() {
        assertEquals(new Position(0,0), Position.of(0, 0));
        assertNotEquals(new Position(0,0), Position.of(1, 1));
    }

    @Test
    void testToString() {
        assertEquals("(0,0)", position.toString());
    }

    @Test
    void testEquals() {
        assertEquals(Position.of(0, 0),position);
        assertNotEquals(Position.of(1, 1),position);
    }

    @Test
    void getRow() {
        assertEquals(0, position.getRow());
        assertNotEquals(1, position.getRow());
    }

    @Test
    void getCol() {
        assertEquals(0, position.getCol());
        assertNotEquals(1, position.getCol());
    }

    @Test
    void setRow() {
        Position newPosition = Position.of(position.getRow(), position.getCol());
        newPosition.setRow(1);
        assertNotEquals(position.getRow(), newPosition.getRow());
        Position positionExpected = new Position(1, 2);
        assertEquals(positionExpected.getRow(), newPosition.getRow());
    }

    @Test
    void setCol() {
        Position newPosition = Position.of(position.getRow(), position.getCol());
        newPosition.setCol(1);
        assertNotEquals(position.getCol(), newPosition.getCol());
        Position positionExpected = new Position(2, 1);
        assertEquals(positionExpected.getCol(), newPosition.getCol());
    }
}