package model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LabyrinthCellTest {

    LabyrinthCell labyrinthCell1 = new LabyrinthCell(Position.of(1, 1));
    LabyrinthCell labyrinthCell2 = new LabyrinthCell(Position.of(1, 7));
    LabyrinthCell labyrinthCell3 = new LabyrinthCell(Position.of(7, 1));
    LabyrinthCell labyrinthCell4 = new LabyrinthCell(Position.of(7, 7));
    LabyrinthCell labyrinthCell5 = new LabyrinthCell(Position.of(2, 2));

    @Test
    void constructor() {
        assertTrue(labyrinthCell1.getDirectionCanGo().containsAll(List.of(Direction.EAST, Direction.SOUTH)));
        assertTrue(labyrinthCell2.getDirectionCanGo().containsAll(List.of(Direction.WEST, Direction.SOUTH)));
        assertTrue(labyrinthCell3.getDirectionCanGo().containsAll(List.of(Direction.NORTH, Direction.EAST)));
        assertTrue(labyrinthCell4.getDirectionCanGo().containsAll(List.of(Direction.NORTH, Direction.WEST)));
        assertTrue(labyrinthCell5.getDirectionCanGo().containsAll(List.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)));
    }

    @Test
    void removeDirection() {
        LabyrinthCell newLabyrinthCell = new LabyrinthCell(Position.of(2, 2));
        newLabyrinthCell.removeDirection(Direction.NORTH);
        assertNotEquals(labyrinthCell5, newLabyrinthCell);
        LabyrinthCell labyrinthCell = new LabyrinthCell(Position.of(2, 2));
        labyrinthCell.getDirectionCanGo().remove(Direction.NORTH);
        assertEquals(labyrinthCell.getDirectionCanGo(), newLabyrinthCell.getDirectionCanGo());
    }

    @Test
    void canGoDirection() {
        assertTrue(labyrinthCell5.canGoDirection(Direction.NORTH));
        assertFalse(labyrinthCell1.canGoDirection(Direction.NORTH));
    }

    @Test
    void getPosition() {
        assertEquals(Position.of(2, 2), labyrinthCell5.getPosition());
        assertNotEquals(Position.of(0, 0), labyrinthCell5.getPosition());
    }

    @Test
    void getDirectionCanGo() {
        assertEquals(Set.of(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST), labyrinthCell5.getDirectionCanGo());
        assertNotEquals(null, labyrinthCell5.getDirectionCanGo());
    }
}