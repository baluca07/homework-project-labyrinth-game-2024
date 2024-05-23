package model;

import lombok.Getter;
import org.tinylog.Logger;
import puzzle.State;

import java.util.HashSet;
import java.util.Set;

public class LabyrinthState implements puzzle.State<Direction> {
    public final static int LABYRINTH_SIZE = 7;
    @Getter
    private Player player;
    private final LabyrinthCell[][] labyrinthCells;

    public LabyrinthState() {
        this.labyrinthCells = new LabyrinthCell[LABYRINTH_SIZE][LABYRINTH_SIZE];
        for (int i = 0; i < LABYRINTH_SIZE; i++) {
            for (int j = 0; j < LABYRINTH_SIZE; j++) {
                labyrinthCells[i][j] = new LabyrinthCell(Position.of(i + 1, j + 1));
            }
        }
    }

    private void setUpPlayer(Position position) { //1 - LABYRINTH_SIZE
        player = new Player(position);
    }

    private void setUpTarget(Position position) {
        Target.getPosition().setRow(position.getRow());
        Target.getPosition().setCol(position.getCol());
    }

    private void setWallBetween(Position position1, Position position2) {
        if (position1.getRow() == position2.getRow()) {
            labyrinthCells[position1.getRow() - 1][position1.getCol() - 1].removeDirection(position1.getCol() < position2.getCol() ? Direction.EAST : Direction.WEST);
            labyrinthCells[position2.getRow() - 1][position2.getCol() - 1].removeDirection(position1.getCol() > position2.getCol() ? Direction.EAST : Direction.WEST);
        }
        if (position1.getCol() == position2.getCol()) {
            labyrinthCells[position1.getRow() - 1][position1.getCol() - 1].removeDirection(position1.getRow() < position2.getRow() ? Direction.SOUTH : Direction.NORTH);
            labyrinthCells[position2.getRow() - 1][position2.getCol() - 1].removeDirection(position1.getRow() > position2.getRow() ? Direction.SOUTH : Direction.NORTH);
        }
    }

    public LabyrinthCell getLabyrinthCellAtPosition(int row, int col) {
        return labyrinthCells[row][col];
    }

    public void movePlayer(Direction direction) {
        if (isSolved()) {
            Logger.info("Solved.");
            return;
        }
        int currentRow = player.getCurrentPosition().getRow();
        int currentCol = player.getCurrentPosition().getCol();
        if (labyrinthCells[currentRow - 1][currentCol - 1].canGoDirection(direction)) {
            Position position = player.getCurrentPosition();
            position.setRow(currentRow + direction.getRowChange());
            position.setCol(currentCol + direction.getColChange());
            movePlayer(direction);
        }
    }

    @Override
    public boolean isSolved() {
        return player.getCurrentPosition().equals(Target.getPosition());
    }

    @Override
    public boolean isLegalMove(Direction direction) {
        var row = player.getCurrentPosition().getRow();
        var col = player.getCurrentPosition().getCol();
        return getLabyrinthCellAtPosition(row, col).canGoDirection(direction);
    }

    @Override
    public void makeMove(Direction direction) {
        movePlayer(direction);
    }

    @Override
    public Set<Direction> getLegalMoves() {
        var row = player.getCurrentPosition().getRow();
        var col = player.getCurrentPosition().getCol();
        return new HashSet<>(getLabyrinthCellAtPosition(row, col).getDirectionCanGo());
    }

    @Override
    public State<Direction> clone() {
        LabyrinthState state = new LabyrinthState();
        int row = player.getCurrentPosition().getRow();
        int col = player.getCurrentPosition().getCol();
        Position clonedPosition = new Position(row, col);
        state.setUpPlayer(clonedPosition);
        return state;
    }
}
