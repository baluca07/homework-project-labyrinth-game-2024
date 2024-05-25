package utils.setup;

import model.Position;

import java.util.Arrays;
import java.util.List;

/**
 * Represent the class needed to read the {@code setup.json} with {@code gson}.
 */
public class LabyrinthSetUp {
    private int[] playerStartPosition;
    private int[] targetPosition;
    private int[][] wallsBetweenPositions;

    /**
     * {@return the player start position converted to {@link Position} type}
     */
    public Position getPlayerStartPosition() {
        return Position.of(playerStartPosition[0], playerStartPosition[1]);
    }

    /**
     * {@return the target position converted to {@link Position} type}
     */
    public Position getTargetPosition() {
        return Position.of(targetPosition[0], targetPosition[1]);
    }

    /**
     * A wall will be placed between the two positions in the arrays. These will be stored in a list.
     *
     * @return returns a list of position arrays
     */
    public List<Position[]> getWallsBetweenPositions() {
        return Arrays.stream(wallsBetweenPositions).map(positionsFileRow -> new Position[]{
                Position.of(positionsFileRow[0], positionsFileRow[1]),
                Position.of(positionsFileRow[2], positionsFileRow[3]),
        }).toList();
    }
}
