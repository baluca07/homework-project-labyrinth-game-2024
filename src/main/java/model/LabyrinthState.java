package model;

import com.google.gson.Gson;
import lombok.Getter;
import org.tinylog.Logger;
import puzzle.State;
import utils.LabyrinthSetUp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a state of the Labyrinth game.
 */
public class LabyrinthState implements puzzle.State<Direction> {
    /**
     * The size of the labyrinth.
     */
    public final static int LABYRINTH_SIZE = 7;
    @Getter
    private Player player;
    private final LabyrinthCell[][] labyrinthCells;

    /**
     * Creates a new maze state and sets it up using the {@link #setUpGame()} method.
     */
    public LabyrinthState() {
        this.labyrinthCells = new LabyrinthCell[LABYRINTH_SIZE][LABYRINTH_SIZE];
        for (int i = 0; i < LABYRINTH_SIZE; i++) {
            for (int j = 0; j < LABYRINTH_SIZE; j++) {
                labyrinthCells[i][j] = new LabyrinthCell(Position.of(i + 1, j + 1));
            }
        }
        setUpGame();
    }

    /**
     * Reads the {@code setup.json} file into an instance of the {@link LabyrinthSetUp} class using {@code gson},
     * then uses the read data to create an instance of the {@link Player} class and set a position of the {@link Target}
     * singleton, and set the inner walls of the labyrinth.
     */
    public void setUpGame() {
        Gson gson = new Gson();
        String json = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().
                getResourceAsStream("/setup.json"))))
                .lines().collect(Collectors.joining());
        LabyrinthSetUp setup = gson.fromJson(json, LabyrinthSetUp.class);
        Logger.info("setup.json read into an instance of the the SetUpLabyrinth class");
        setUpPlayer(setup.getPlayerStartPosition());
        setUpTarget(setup.getTargetPosition());
        for (var positions : setup.getWallsBetweenPositions()) {
            setWallBetween(positions[0], positions[1]);
        }
        Logger.info("Game set up");
    }

    private void setUpPlayer(Position position) { //1 - LABYRINTH_SIZE
        player = new Player(position);
        Logger.debug("Player set up:" + position);
    }

    private void setUpTarget(Position position) {
        Target.getPosition().setRow(position.getRow());
        Target.getPosition().setCol(position.getCol());
        Logger.debug("Target set up:" + Target.getPosition());
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
        Logger.debug("Wall set up between " + position1 + " and " + position2);
    }

    /**
     * {@return the labyrinth cell in the specified position}
     *
     * @param position the position on which we want to return the cell
     */
    public LabyrinthCell getLabyrinthCellAtPosition(Position position) {
        var row = position.getRow() - 1;
        var col = position.getCol() - 1;
        return labyrinthCells[row][col];
    }

    /**
     * {@return whether the labyrinth is solved, i.e. whether the player
     * is at the {@link Target} position}
     */
    @Override
    public boolean isSolved() {
        return player.getCurrentPosition().equals(Target.getPosition());
    }

    /**
     * {@return whether it is possible to move the player in the specified direction}
     *
     * @param direction direction we want to check
     */
    @Override
    public boolean isLegalMove(Direction direction) {
        var row = player.getCurrentPosition().getRow();
        var col = player.getCurrentPosition().getCol();
        return getLabyrinthCellAtPosition(Position.of(row, col)).canGoDirection(direction);
    }

    /**
     * The player takes the step in the given direction. If the game has been resolved,
     * no move is made by the player.
     *
     * @param direction the direction in which you want to move the player
     */
    @Override
    public void makeMove(Direction direction) {
        if (isSolved()) {
            Logger.info("Already solved");
            return;
        }
        int currentRow = player.getCurrentPosition().getRow();
        int currentCol = player.getCurrentPosition().getCol();
        if (labyrinthCells[currentRow - 1][currentCol - 1].canGoDirection(direction)) {
            Position position = player.getCurrentPosition();
            position.setRow(currentRow + direction.getRowChange());
            position.setCol(currentCol + direction.getColChange());
            makeMove(direction);
        }
        Logger.info("Player moved to the " + direction +
                ".\t Old position: " + Position.of(currentRow, currentCol) +
                "\t New position: " + player.getCurrentPosition());
    }

    /**
     * {@return a {@code Set} of direction in which direction the player can move}
     */
    @Override
    public Set<Direction> getLegalMoves() {
        var row = player.getCurrentPosition().getRow();
        var col = player.getCurrentPosition().getCol();
        return new HashSet<>(getLabyrinthCellAtPosition(Position.of(row, col)).getDirectionCanGo());
    }

    /**
     * {@return a copy of the current state}
     */
    @Override
    public State<Direction> clone() {
        LabyrinthState state = new LabyrinthState();
        int row = player.getCurrentPosition().getRow();
        int col = player.getCurrentPosition().getCol();
        Position clonedPosition = new Position(row, col);
        state.setUpPlayer(clonedPosition);
        return state;
    }

    @Override
    public String toString() {
        return "Labyrinth state: " +
                player.toString() +
                ", solved=" + isSolved() +
                ", legalMoves=" + getLegalMoves();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabyrinthState that = (LabyrinthState) o;
        return Objects.equals(player.getCurrentPosition(), that.player.getCurrentPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getCurrentPosition());
    }
}
