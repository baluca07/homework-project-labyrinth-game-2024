package model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LabyrinthStateTest {

    LabyrinthState labyrinthState = new LabyrinthState();

    @Test
    void getLabyrinthCellAtPosition() {
        assertDoesNotThrow(() -> labyrinthState.getLabyrinthCellAtPosition(Position.of(7, 7)));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> labyrinthState.getLabyrinthCellAtPosition(Position.of(0, 0)));
    }

    @Test
    void isSolved() {
        LabyrinthState labyrinthState1 = (LabyrinthState) labyrinthState.clone();
        assertFalse(labyrinthState1.isSolved());
        Position targetPosition = Target.getPosition();
        labyrinthState1.getPlayer().getCurrentPosition().setRow(targetPosition.getRow());
        labyrinthState1.getPlayer().getCurrentPosition().setCol(targetPosition.getCol());
        assertTrue(labyrinthState1.isSolved());
    }

    @Test
    void isLegalMove() {
        LabyrinthState labyrinthState1 = (LabyrinthState) labyrinthState.clone();
        assertTrue(labyrinthState1.isLegalMove(Direction.SOUTH));
        assertFalse(labyrinthState1.isLegalMove(Direction.NORTH));
    }

    @Test
    void makeMove() {
        LabyrinthState labyrinthStateStart = (LabyrinthState) labyrinthState.clone();
        LabyrinthState labyrinthStateMoved = (LabyrinthState) labyrinthStateStart.clone();
        labyrinthStateMoved.makeMove(Direction.SOUTH);
        assertNotEquals(labyrinthStateStart,labyrinthStateMoved);
        assertEquals(Position.of(7,1),labyrinthStateMoved.getPlayer().getCurrentPosition());
    }

    @Test
    void getLegalMoves() {
        LabyrinthCell labyrinthCell = labyrinthState.getLabyrinthCellAtPosition(Position.of(2,2));
        assertEquals(Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST),labyrinthCell.getDirectionCanGo());
    }

    @Test
    void testClone() {
        LabyrinthState labyrinthStateCloned = (LabyrinthState) labyrinthState.clone();
        assertEquals(labyrinthState.getPlayer().getCurrentPosition(),labyrinthStateCloned.getPlayer().getCurrentPosition());
    }
}