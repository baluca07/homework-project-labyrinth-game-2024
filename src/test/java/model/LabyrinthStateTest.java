package model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static model.LabyrinthState.LABYRINTH_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class LabyrinthStateTest {

    LabyrinthState labyrinthState = new LabyrinthState();

    @Test
    void getLabyrinthCellAtPosition() {
        assertDoesNotThrow(() -> labyrinthState.getLabyrinthCellAtPosition(Position.of(LABYRINTH_SIZE, LABYRINTH_SIZE)));
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
        labyrinthState1.makeMove(Direction.SOUTH);
        assertFalse(labyrinthState1.isLegalMove(Direction.SOUTH));
    }

    @Test
    void makeMove() {
        LabyrinthState labyrinthStateStart = (LabyrinthState) labyrinthState.clone();
        LabyrinthState labyrinthStateMoved = (LabyrinthState) labyrinthStateStart.clone();
        labyrinthStateMoved.makeMove(Direction.SOUTH);
        assertNotEquals(labyrinthStateStart, labyrinthStateMoved);
        assertEquals(Position.of(5, 5), labyrinthStateMoved.getPlayer().getCurrentPosition());
    }

    @Test
    void getLegalMoves() {
        LabyrinthState labyrinthStateCloned = (LabyrinthState) labyrinthState.clone();
        assertEquals(Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST), labyrinthStateCloned.getLegalMoves());
        labyrinthStateCloned.makeMove(Direction.SOUTH);
        assertNotEquals(Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST), labyrinthStateCloned.getLegalMoves());
    }

    @Test
    void testClone() {
        LabyrinthState labyrinthStateCloned = (LabyrinthState) labyrinthState.clone();
        assertEquals(labyrinthState.getPlayer().getCurrentPosition(), labyrinthStateCloned.getPlayer().getCurrentPosition());
    }


    @Test
    void getPlayerWonProperty() {
        assertEquals(false, labyrinthState.getPlayerWonProperty().getValue());

        LabyrinthState labyrinthState1 = (LabyrinthState) labyrinthState.clone();
        Position targetPosition = Target.getPosition();
        labyrinthState1.getPlayer().getCurrentPosition().setRow(targetPosition.getRow());
        labyrinthState1.getPlayer().getCurrentPosition().setCol(targetPosition.getCol());

        assertEquals(false, labyrinthState.getPlayerWonProperty().getValue());
    }
}