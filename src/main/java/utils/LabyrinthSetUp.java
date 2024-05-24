package utils;

import model.Position;

import java.util.Arrays;
import java.util.List;

public class LabyrinthSetUp {
    private int[] playerStartPosition;
    private int[] targetPosition;
    private int[][] wallsBetweenPositions;

    public Position getPlayerStartPosition() {
        return Position.of(playerStartPosition[0],playerStartPosition[1]);
    }
    public Position getTargetPosition(){
        return Position.of(targetPosition[0],targetPosition[1]);
    }
    public List<Position[]> getWallsBetweenPositions(){
        return Arrays.stream(wallsBetweenPositions).map(positionsFileRow -> new Position[] {
                Position.of(positionsFileRow[0], positionsFileRow[1]),
                Position.of(positionsFileRow[2], positionsFileRow[3]),
        }).toList();
    }
}
